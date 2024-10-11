package com.traineemanagement.userservice.repository;

import com.traineemanagement.userservice.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


@NoRepositoryBean
public interface BaseUserRepository<UserCredential extends BaseUser> extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserCredential> findByPassword(String password);
}
