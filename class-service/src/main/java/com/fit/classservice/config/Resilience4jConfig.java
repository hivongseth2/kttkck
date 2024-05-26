package com.fit.classservice.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class Resilience4jConfig {

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(5))
                .build();
        log.info("RetryConfig loaded: " + retryConfig.toString());
        return RetryRegistry.of(retryConfig);
    }

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(60))
                .limitForPeriod(5)
                .timeoutDuration(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public RateLimiter rateLimiter() {
        RateLimiterConfig config = rateLimiterConfig();
        return RateLimiter.of("myLimiter", config);
    }

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .build();
    }
}
