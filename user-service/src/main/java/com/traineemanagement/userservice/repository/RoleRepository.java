package com.traineemanagement.userservice.repository;

import com.traineemanagement.userservice.model.Role;
import com.traineemanagement.userservice.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
