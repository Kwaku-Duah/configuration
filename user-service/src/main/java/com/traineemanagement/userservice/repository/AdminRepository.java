package com.traineemanagement.userservice.repository;

import com.traineemanagement.userservice.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends BaseUserRepository<Admin> {
}
