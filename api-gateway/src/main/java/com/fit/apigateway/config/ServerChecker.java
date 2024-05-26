//package com.fit.apigateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import java.time.Duration;
//
//@Component
//public class ServerChecker {
//
//    private final WebClient webClient;
//    private final ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory;
//
//    @Autowired
//    public ServerChecker(WebClient.Builder webClientBuilder, ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8761").build();
//        this.circuitBreakerFactory = circuitBreakerFactory;
//    }
//
//    public Mono<Boolean> checkServer() {
//        return webClient.get()
//                .uri("/eureka/web")
//                .retrieve()
//                .onStatus(HttpStatus::is2xxSuccessful, response -> Mono.just(true))
//                .bodyToMono(Void.class)
//                .timeout(Duration.ofSeconds(15))
//                .onErrorResume(error -> Mono.just(false))
//                .retryBackoff(3, Duration.ofSeconds(15))
//                .transform(circuitBreakerFactory::apply);
//    }
//}
//
//
