package com.fit.userservice.security.jwt;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class UserJwt implements Serializable {
    private String username;
}

