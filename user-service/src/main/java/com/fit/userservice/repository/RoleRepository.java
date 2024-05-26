package com.fit.userservice.repository;

import com.fit.userservice.entity.ERole;
import com.fit.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(ERole role);

    Optional<Role> findByName(String role);
}
