package com.fit.apigateway.security.service;

import com.fit.apigateway.entity.User;
import com.fit.apigateway.exception.NotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class UserService implements UserDetailsService {
    private final com.fit.apigateway.service.UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> {
                    throw new NotFoundException(404, "User is not found!");
                });
        if (!user.isActive()) {
            throw new NotFoundException(404, "User is not active!");
        }

        return UserDetailsImpl.build(user);
    }
}
