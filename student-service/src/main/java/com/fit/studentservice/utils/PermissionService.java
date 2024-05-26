package com.fit.studentservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.studentservice.model.request.CheckPermissionRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {
    private final RestTemplate restTemplate;

    @Retry(name = "checkPermission", fallbackMethod = "fallbackCheckPermission")
    @RateLimiter(name = "checkPermission")
    @CircuitBreaker(name = "checkPermission", fallbackMethod = "fallbackCheckPermission")
    public boolean checkPermission(String token, List<String> roles) {
        String url = "http://user-service:8001/api/user/check-permission";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CheckPermissionRequest request = CheckPermissionRequest.builder()
                .token(token)
                .roles(roles)
                .build();

        HttpEntity<CheckPermissionRequest> entity = new HttpEntity<>(request, headers);

        try {
            return restTemplate.postForObject(url, entity, Boolean.class);
        } catch (HttpServerErrorException e) {
            // Log the detailed server error
            log.error("HTTP Status Code: " + e.getStatusCode());
            log.error("Response Body: " + e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            log.error("Error occurred while checking permission", e);
            return false;
        }
    }

    public boolean fallbackCheckPermission(String token, List<String> roles, Throwable t) {
        log.warn("Fallback method called due to exception: ", t);
        return false;
    }
}

