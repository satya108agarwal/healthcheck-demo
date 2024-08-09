package com.healthcheck.healthcheck_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Random;

@Component
public class CacheWarmUp {

    @Autowired
    private CacheManager cacheManager;

    private final Random random = new Random();

    @PostConstruct
    public void warmUpCache() {
        System.out.println("Cache warm-up initiated...");

        Cache cache = cacheManager.getCache("testCache");
        cache.put("testKey", "testValue");
        System.out.println("Cache populated successfully.");

        // Simulate delay to complete cache warm-up
        try {
            System.out.println("Simulating cache warm-up delay...");
            Thread.sleep(180000); // Sleep for 3 minutes
        } catch (InterruptedException e) {
            System.err.println("Cache warm-up interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("Cache warm-up completed.");
    }
}
