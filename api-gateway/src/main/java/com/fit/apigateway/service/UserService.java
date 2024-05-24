package com.fit.apigateway.service;

import com.fit.apigateway.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<User> findByUsername(String username);
}
