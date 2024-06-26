package com.fit.classservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.classservice.model.CheckPermissionRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {
    private final RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        log.info("Proxy class: " + AopProxyUtils.ultimateTargetClass(this).getName());
    }


//    @RateLimiter(name = "checkPermission")
//    @CircuitBreaker(name = "checkPermission", fallbackMethod = "fallbackCheckPermission")
    public boolean checkPermission(String token, List<String> roles) {
        log.info("checkPermission method called");

        String url = "http://user-service:8001/api/user/check-permission";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CheckPermissionRequest request = CheckPermissionRequest.builder()
                .token(token)
                .roles(roles)
                .build();

        HttpEntity<CheckPermissionRequest> entity = new HttpEntity<>(request, headers);

        // Log the request
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(request);
            log.info("JSON Request: " + jsonRequest);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON", e);
        }

        try {
            return restTemplate.postForObject(url, entity, Boolean.class);
        } catch (HttpServerErrorException e) {
            log.error("HTTP Status Code: " + e.getStatusCode());
            log.error("Response Body: " + e.getResponseBodyAsString());
            throw e; // rethrow to trigger retry
        } catch (Exception e) {
            log.error("Error occurred while checking permission", e);
            throw e; // rethrow to trigger retry
        }
    }

    public boolean fallbackCheckPermission(String token, List<String> roles, Throwable t) {
        log.warn("Fallback method called due to exception: ", t);
        return false;
    }
}


