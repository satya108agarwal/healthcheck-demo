package com.healthcheck.healthcheck_demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * CustomReadinessIndicator is a custom implementation of the ReadinessStateHealthIndicator.
 * It checks the readiness state of the application to determine if it is ready to accept traffic.
 */
@Component
public class CustomReadinessIndicator extends ReadinessStateHealthIndicator {


    @Autowired
    private H2Repository h2Repository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RestTemplate restTemplate;

    private final Random random = new Random();

    /**
     * Constructor that initializes the CustomReadinessIndicator with the ApplicationAvailability instance.
     *
     * @param availability the application availability instance
     */

    public CustomReadinessIndicator(ApplicationAvailability availability) {
        super(availability);
    }

    /**
     * Overrides the getState method to provide custom logic for determining the readiness state.
     *
     * @param applicationAvailability the application availability instance
     * @return the readiness state of the application
     */
    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        try {
            return checkReadiness()
                    ? ReadinessState.ACCEPTING_TRAFFIC
                    : ReadinessState.REFUSING_TRAFFIC;
        } catch (Exception e) {
            // Log unexpected exceptions
            System.err.println("Unexpected error during readiness check: " + e.getMessage());
            return ReadinessState.REFUSING_TRAFFIC;
        }
    }

    /**
     * Custom method to check the readiness of the application.
     *
     * @return true if the application is ready to accept traffic, false otherwise
     */

    private boolean checkReadiness() {
        System.out.println("Readiness check initiated...");

        // Introduce a random failure 20% of the time for readiness check
        if (random.nextInt(5) < 1) {
            System.err.println("Readiness check failed: Random readiness failure");
            return false;
        }

        try {

            // Check H2 Database
            System.out.println("Checking H2 Database...");
            h2Repository.findAll();
            System.out.println("H2 Database check passed.");

            // Check Cache (using in-memory ConcurrentMapCacheManager)
            System.out.println("Checking Cache...");
            if (cacheManager.getCache("testCache").get("testKey") == null) {
                throw new RuntimeException("Cache is not populated");
            }
            System.out.println("Cache check passed.");

            // Check Third-party Service (using a public API)
            System.out.println("Checking external API...");
            restTemplate.getForEntity("https://api.github.com", String.class);
            System.out.println("External API check passed.");

            // If all checks pass, consider the application ready
            System.out.println("Readiness check passed.");
            return true;
        } catch (Exception e) {
            System.err.println("Readiness check failed: " + e.getMessage());
            // If any of the checks fail, the application is not ready
            return false;
        }
    }
}