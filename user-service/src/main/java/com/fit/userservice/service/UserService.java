package com.fit.userservice.service;

import com.fit.userservice.entity.User;
import com.fit.userservice.model.request.UserRequest;
import com.fit.userservice.model.response.JwtResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    JwtResponse login(String username, String password, String type);

    void createUser(UserRequest request);

    boolean checkPermission(String token, String role);
}
