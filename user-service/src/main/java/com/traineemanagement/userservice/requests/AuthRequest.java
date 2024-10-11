package com.traineemanagement.userservice.requests;
import lombok.Getter;

@Getter
public class AuthRequest {
    private String email;
    private String password;
}