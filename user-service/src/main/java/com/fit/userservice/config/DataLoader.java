package com.fit.userservice.config;

import com.fit.userservice.entity.ERole;
import com.fit.userservice.entity.Role;
import com.fit.userservice.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    ApplicationListener<ContextRefreshedEvent> init() {
        return event -> {
            List<ERole> roles = Arrays.asList(ERole.STUDENT, ERole.ADMIN, ERole.LECTURER);

            RoleRepository roleRepository = event.getApplicationContext().getBean(RoleRepository.class);

            roles.forEach(role -> {
                if (!roleRepository.existsByName(role)) {
                    Role newRole = new Role(role, "This is role: " + role.name().toLowerCase());
                    roleRepository.save(newRole);
                }
            });
        };
    }
}
