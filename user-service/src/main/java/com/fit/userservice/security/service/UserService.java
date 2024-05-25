package com.fit.userservice.security.service;

import com.fit.userservice.entity.User;
import com.fit.userservice.exception.NotFoundException;
import com.fit.userservice.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new NotFoundException(404, "User is not found!");
                });
        if (!user.isActive()) {
            throw new NotFoundException(404, "User is not active!");
        }

        return UserDetailsImpl.build(user);
    }
}
