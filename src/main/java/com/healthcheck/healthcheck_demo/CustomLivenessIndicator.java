package com.healthcheck.healthcheck_demo;

import org.springframework.boot.actuate.availability.LivenessStateHealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.stereotype.Component;

/**
 * CustomLivenessIndicator is a custom implementation of the LivenessStateHealthIndicator.
 * It checks the liveness state of the application to determine if it is healthy and able
 * to continue serving traffic.
 */
@Component
public class CustomLivenessIndicator extends LivenessStateHealthIndicator {


    /**
     * Constructor that initializes the CustomLivenessIndicator with the ApplicationAvailability instance.
     *
     * @param availability the application availability instance
     */
    public CustomLivenessIndicator(ApplicationAvailability availability) {
        super(availability);
    }

    /**
     * Overrides the getState method to provide custom logic for determining the liveness state.
     *
     * @param applicationAvailability the application availability instance
     * @return the liveness state of the application
     */
    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        try {
            return checkLiveness()
                    ? LivenessState.CORRECT
                    : LivenessState.BROKEN;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Custom method to check the liveness of the application.
     * This method simulates a liveness check by sleeping for 3 seconds.
     *
     * @return true if the application is live and can continue to serve traffic, false otherwise
     * @throws InterruptedException if the sleep is interrupted
     */
    private boolean checkLiveness() throws InterruptedException {
        // Implement your readiness check logic here
        System.out.println("checking if the application is live to continue to serve traffic. ");
        Thread.sleep(3000);
        System.out.println("Slept for 3 seconds ...  to simulate checking if the application is live to continue traffic. ");
        return true; // Example check
    }
}
