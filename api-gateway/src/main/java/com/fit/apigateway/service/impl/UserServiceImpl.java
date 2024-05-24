package com.fit.apigateway.service.impl;

import com.fit.apigateway.entity.User;
import com.fit.apigateway.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
