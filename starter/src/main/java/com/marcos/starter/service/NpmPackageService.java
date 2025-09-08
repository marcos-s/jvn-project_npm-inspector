package com.marcos.starter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class NpmPackageService {

    private final WebClient webClient;

    public NpmPackageService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://registry.npmjs.org")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB buffer
                .build();
    }

    public Mono<String> fetchPackageData(String packageName) {
        return webClient.get()
                .uri("/{packageName}", packageName)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), 
                    response -> Mono.error(new RuntimeException("Package '" + packageName + "' not found")))
                .onStatus(status -> status.is5xxServerError(), 
                    response -> Mono.error(new RuntimeException("NPM Registry API is currently unavailable")))
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> 
                    Mono.error(new RuntimeException("Failed to fetch package data: " + ex.getMessage())));
    }
}
