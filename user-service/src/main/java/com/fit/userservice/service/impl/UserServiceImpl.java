package com.fit.userservice.service.impl;

import com.fit.userservice.entity.User;
import com.fit.userservice.exception.BadRequestException;
import com.fit.userservice.exception.NotFoundException;
import com.fit.userservice.model.request.CheckPermissionRequest;
import com.fit.userservice.model.request.UserRequest;
import com.fit.userservice.model.response.JwtResponse;
import com.fit.userservice.repository.UserRepository;
import com.fit.userservice.security.jwt.JwtService;
import com.fit.userservice.service.UserService;
import com.fit.userservice.utils.Authen;
import com.fit.userservice.utils.BcryptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public JwtResponse login(String username, String password, String type) {
        String usernameMatcher = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(usernameMatcher);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new BadRequestException(400, "Username is not valid!");
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new NotFoundException(404, "User is not found!");
        });

        if (!user.isActive()) {
            throw new BadRequestException(400, "User is not active!");
        }

        if (BcryptUtils.verifyPassword(password, user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());

            List<String> authorities = user.getRoles().stream()
                    .map(u -> u.getName().name())
                    .toList();

            return JwtResponse.builder()
                    .token(token)
                    .type("Bearer")
                    .id(user.getId())
                    .username(user.getUsername())
                    .role(authorities)
                    .build();
        } else throw new BadRequestException(400, "Password is not correct!");
    }

    @Override
    public void createUser(UserRequest request) {
        String usernameMatcher = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(usernameMatcher);
        Matcher matcher = pattern.matcher(request.getUsername());
        if (!matcher.matches()) {
            throw new BadRequestException(400, "Username is not valid!");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException(400, "Username is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(BcryptUtils.hashPassword(request.getPassword()));
        newUser.setActive(true);

        userRepository.save(newUser);
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null; // or throw an exception if needed
    }

    @Override
    public boolean checkPermission(CheckPermissionRequest request) {
        String token = extractToken(request.getToken());
        System.out.println("Token: " + token);
        User user = userRepository.findByUsername(jwtService.getUsernameFromToken(token)).get();

        return user.getRoles().stream()
                .anyMatch(u -> request.getRoles().contains(u.getName().name()));
    }
}