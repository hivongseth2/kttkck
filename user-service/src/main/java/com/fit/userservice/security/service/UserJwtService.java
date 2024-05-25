package com.fit.userservice.security.service;

import com.fit.userservice.security.jwt.JwtService;
import com.fit.userservice.security.jwt.UserJwt;
import org.springframework.stereotype.Service;

@Service
public class UserJwtService extends JwtService<UserJwt> {
}

