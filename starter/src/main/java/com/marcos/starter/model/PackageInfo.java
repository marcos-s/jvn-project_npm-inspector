package com.marcos.starter.model;

import java.time.LocalDateTime;
import java.util.Map;

public class PackageInfo {
    private String name;
    private String version;
    private String license;
    private long weeklyDownloads;
    private int dependencyCount;
    private long unpackedSize;
    private LocalDateTime lastPublishDate;
    private String description;
    private Map<String, String> distTags;

    // Constructors, getters, and setters
    public PackageInfo() {}

    public PackageInfo(String name, String version, String license, long weeklyDownloads, 
                      int dependencyCount, long unpackedSize, LocalDateTime lastPublishDate, 
                      String description, Map<String, String> distTags) {
        this.name = name;
        this.version = version;
        this.license = license;
        this.weeklyDownloads = weeklyDownloads;
        this.dependencyCount = dependencyCount;
        this.unpackedSize = unpackedSize;
        this.lastPublishDate = lastPublishDate;
        this.description = description;
        this.distTags = distTags;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    
    public String getLicense() { return license; }
    public void setLicense(String license) { this.license = license; }
    
    public long getWeeklyDownloads() { return weeklyDownloads; }
    public void setWeeklyDownloads(long weeklyDownloads) { this.weeklyDownloads = weeklyDownloads; }
    
    public int getDependencyCount() { return dependencyCount; }
    public void setDependencyCount(int dependencyCount) { this.dependencyCount = dependencyCount; }
    
    public long getUnpackedSize() { return unpackedSize; }
    public void setUnpackedSize(long unpackedSize) { this.unpackedSize = unpackedSize; }
    
    public LocalDateTime getLastPublishDate() { return lastPublishDate; }
    public void setLastPublishDate(LocalDateTime lastPublishDate) { this.lastPublishDate = lastPublishDate; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Map<String, String> getDistTags() { return distTags; }
    public void setDistTags(Map<String, String> distTags) { this.distTags = distTags; }
}
