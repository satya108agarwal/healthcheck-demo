# Custom Health Checks in Spring Boot

This project demonstrates how to implement custom liveness and readiness health checks in a Spring Boot application. Health checks are critical for maintaining the reliability and stability of your application, especially in distributed and containerized environments.

## Overview

In a Spring Boot application, health checks are used to monitor the application's health status. They provide insights into whether the application is functioning correctly and can handle traffic. This project implements custom liveness and readiness health checks using Spring Boot's Actuator module.

### Custom Health Checks

1. **Liveness Check**:
    - **Purpose**: Determines if the application is alive and running. This check ensures that the application process is still running and has not crashed.
    - **Implementation**: The `CustomLivenessIndicator` class extends `LivenessStateHealthIndicator` and performs a custom liveness check.

2. **Readiness Check**:
    - **Purpose**: Determines if the application is ready to accept traffic. This check ensures that the application is fully initialized and can handle incoming requests.
    - **Implementation**: The `CustomReadinessIndicator` class extends `ReadinessStateHealthIndicator` and performs a custom readiness check.

### Importance of Health Checks

### Why Check All Critical Services?

Health checks are not limited to just the application's status. They are crucial for several reasons:

**Upstream Services**: Ensuring that your application can communicate with upstream services (e.g., databases, APIs) is critical. If these services are down or slow, it can impact your application's ability to function properly.

**Downstream Services**: Verifying that downstream services (e.g., message queues, external integrations) are operational helps maintain overall system reliability and prevents cascading failures.

**Prevent Downtime**: By implementing comprehensive health checks, you can detect issues early and take corrective actions before they affect your users.

**Automated Monitoring**: Health checks are often integrated with monitoring and alerting systems to provide real-time feedback and automated responses to failures.


### Run in local environment

```bash
./gradlew clean build bootRun
```


**Note**:

- The application will be pushed using settings in the provided `manifest.yml` file. The output from the command will show the URL that has been assigned to the application.
- Edit the manifest file to change the application name to include a unique ID (May be Org Employee ID) to meet Cloud Foundry's requirement for unique app names
- Use manifest-TAS-6.0.yml to test readiness probes, as readiness probes are only supported from TAS 6.0 and above
### Deploy in Tanzu

```bash
cf push
```

## Health Check Configuration

### `health-check-http-endpoint`

- **Description**: HTTP endpoint for performing liveness checks.
- **Value**: `/actuator/health/liveness`
- **Purpose**: Cloud Foundry will send HTTP requests to this endpoint to check if the application is live and able to handle traffic.

### `health-check-type`

- **Description**: Type of health check.
- **Value**: `http`
- **Purpose**: Specifies that HTTP requests are used to perform the health check.

### `health-check-invocation-timeout`

- **Description**: Timeout for the health check request.
- **Value**: `10`
- **Purpose**: Sets a 10-second limit for the health check response. If the response is not received within this time, the check will be considered failed.

## Readiness Check Configuration

### `readiness-health-check-http-endpoint`

- **Description**: HTTP endpoint for performing readiness checks.
- **Value**: `/actuator/health/readiness`
- **Purpose**: Cloud Foundry will send HTTP requests to this endpoint to verify if the application is ready to accept traffic.

### `readiness-health-check-type`

- **Description**: Type of readiness check.
- **Value**: `http`
- **Purpose**: Specifies that HTTP requests are used for the readiness check.

### `readiness-health-check-invocation-timeout`

- **Description**: Timeout for the readiness check request.
- **Value**: `10`
- **Purpose**: Sets a 10-second limit for the readiness check response. If the response is not received within this time, the check will be considered failed.


### Conclusion
Custom health checks are vital for maintaining the health and reliability of your Spring Boot applications. By following best practices and ensuring comprehensive checks, you can enhance the robustness and resilience of your system.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Health check TPCF](https://docs.vmware.com/en/VMware-Tanzu-Application-Service/6.0/tas-for-vms/deploy-apps-healthchecks.html)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.2/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.2/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#web)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

