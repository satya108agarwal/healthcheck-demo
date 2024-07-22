package com.healthcheck.healthcheck_demo;

import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.stereotype.Component;
/**
 * CustomReadinessIndicator is a custom implementation of the ReadinessStateHealthIndicator.
 * It checks the readiness state of the application to determine if it is ready to accept traffic.
 */
@Component
public class CustomReadinessIndicator extends ReadinessStateHealthIndicator {

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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Custom method to check the readiness of the application.
     * This method simulates a readiness check by sleeping for 3 seconds.
     *
     * @return true if the application is ready to accept traffic, false otherwise
     * @throws InterruptedException if the sleep is interrupted
     */
    private boolean checkReadiness() throws InterruptedException {
        // Implement your readiness check logic here
        System.out.println("checking if the application is ready to take traffic. ");
        Thread.sleep(3000);
        System.out.println("Slept for 3 seconds ...  to simulate checking if the application is ready to take traffic. ");
        return true; // Example check
    }
}
