package com.traineemanagement.userservice.dto.userdto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private boolean active;
    private String token;
    private Long tokenExpiration;

    public LoginResponseDto(Long id, String firstName, String lastName, String email, String role, boolean active, String token, Long tokenExpiration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.active = active;
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }
}
