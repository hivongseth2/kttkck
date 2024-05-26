package com.fit.studentservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3) // thử lại tối đa 3 lần
                .waitDuration(Duration.ofSeconds(5)) // thời gian chờ giữa các lần thử
                .build();
    }

    @Bean
    public RateLimiterConfig rateLimiterConfig() { // Giới hạn số lượng lời gọi trong một khoảng thời gian nhất định.
        return RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(5)) // khoảng thời gian giữa các lần reset
                .limitForPeriod(20) // số lần gọi tối đa trong một khoảng thời gian
                .timeoutDuration(Duration.ofSeconds(5)) // thời gian chờ trước khi timeout
                .build();
    }

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() { // Ngắt kết nối khi tỷ lệ lỗi vượt quá ngưỡng
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // ngưỡng tỷ lệ lỗi
                .waitDurationInOpenState(Duration.ofMillis(1000)) // thời gian chờ trước khi mở lại kết nối
                .slidingWindowSize(2) // số lần gọi được xem xét để tính tỷ lệ lỗi
                .build();
    }
}
