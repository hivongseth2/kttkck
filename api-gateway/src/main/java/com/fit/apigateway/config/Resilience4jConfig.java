//package com.fit.apigateway.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import io.github.resilience4j.retry.RetryConfig;
//import io.github.resilience4j.retry.RetryRegistry;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
//@Configuration
//public class Resilience4jConfig {
//
//    @Bean
//    public RetryRegistry retryRegistry() {
//        RetryConfig retryConfig = RetryConfig.custom()
//                .maxAttempts(3)
//                .waitDuration(Duration.ofMillis(500))
//                .build();
//        return RetryRegistry.of(retryConfig);
//    }
//
//    @Bean
//    public CircuitBreakerRegistry circuitBreakerRegistry() {
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .waitDurationInOpenState(Duration.ofMillis(5000))
//                .permittedNumberOfCallsInHalfOpenState(2)
//                .slidingWindowSize(4)
//                .build();
//        return CircuitBreakerRegistry.of(circuitBreakerConfig);
//    }
//}
