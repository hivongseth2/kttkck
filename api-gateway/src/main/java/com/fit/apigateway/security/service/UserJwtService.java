package com.fit.apigateway.security.service;

import com.fit.apigateway.security.jwt.JwtService;
import com.fit.apigateway.security.jwt.UserJwt;
import org.springframework.stereotype.Service;

@Service
public class UserJwtService extends JwtService<UserJwt> {
}

