package com.fit.apigateway.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String numberPhone;
    private boolean active;
    private String fullName;
    private boolean gender;
    private LocalDate birthday;
    private Set<Role> roles;
}

