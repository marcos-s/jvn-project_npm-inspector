package com.marcos.starter.controller;

import com.marcos.starter.model.PackageInfo;
import com.marcos.starter.service.NpmPackageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "http://localhost:8080")
public class PackageController {

    private final NpmPackageService npmPackageService;

    public PackageController(NpmPackageService npmPackageService) {
        this.npmPackageService = npmPackageService;
    }

    @GetMapping("/{packageName}")
    public Mono<PackageInfo> getPackageData(@PathVariable String packageName) {
        return npmPackageService.fetchPackageData(packageName);
    }
}
