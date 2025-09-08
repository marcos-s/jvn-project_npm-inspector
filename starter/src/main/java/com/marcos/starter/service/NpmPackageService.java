package com.marcos.starter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcos.starter.model.PackageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class NpmPackageService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public NpmPackageService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://registry.npmjs.org")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public Mono<PackageInfo> fetchPackageData(String packageName) {
        return webClient.get()
                .uri("/{packageName}", packageName)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), 
                    response -> Mono.error(new RuntimeException("Package '" + packageName + "' not found")))
                .onStatus(status -> status.is5xxServerError(), 
                    response -> Mono.error(new RuntimeException("NPM Registry API is currently unavailable")))
                .bodyToMono(String.class)
                .map(this::parsePackageData)
                .onErrorResume(WebClientResponseException.class, ex -> 
                    Mono.error(new RuntimeException("Failed to fetch package data: " + ex.getMessage())));
    }

    private PackageInfo parsePackageData(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            // Extract basic information
            String name = root.get("name").asText();
            String latestVersion = root.get("dist-tags").get("latest").asText();
            String description = root.get("description").asText();
            
            // Extract license from latest version
            JsonNode latestVersionData = root.get("versions").get(latestVersion);
            String license = extractLicense(latestVersionData);
            
            // Extract dependency count
            int dependencyCount = 0;
            if (latestVersionData.has("dependencies")) {
                dependencyCount = latestVersionData.get("dependencies").size();
            }
            
            // Extract unpacked size
            long unpackedSize = 0;
            if (latestVersionData.has("dist") && latestVersionData.get("dist").has("unpackedSize")) {
                unpackedSize = latestVersionData.get("dist").get("unpackedSize").asLong();
            }
            
            // Extract weekly downloads
            long weeklyDownloads = 0;
            if (root.has("downloads") && root.get("downloads").has("last-week")) {
                weeklyDownloads = root.get("downloads").get("last-week").asLong();
            }
            
            // Extract last publish date
            LocalDateTime lastPublishDate = null;
            if (root.has("time") && root.get("time").has(latestVersion)) {
                String dateString = root.get("time").get(latestVersion).asText();
                lastPublishDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
            }
            
            // Extract dist-tags
            Map<String, String> distTags = new HashMap<>();
            if (root.has("dist-tags")) {
                root.get("dist-tags").fieldNames().forEachRemaining(fieldName -> 
                    distTags.put(fieldName, root.get("dist-tags").get(fieldName).asText()));
            }
            
            return new PackageInfo(name, latestVersion, license, weeklyDownloads, 
                                 dependencyCount, unpackedSize, lastPublishDate, 
                                 description, distTags);
                                 
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse package data: " + e.getMessage());
        }
    }
    
    private String extractLicense(JsonNode versionData) {
        if (versionData.has("license")) {
            JsonNode license = versionData.get("license");
            if (license.isTextual()) {
                return license.asText();
            } else if (license.isObject() && license.has("type")) {
                return license.get("type").asText();
            }
        }
        return "Unknown";
    }
}
