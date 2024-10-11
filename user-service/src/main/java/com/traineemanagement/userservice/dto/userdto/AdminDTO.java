package com.traineemanagement.userservice.dto.userdto;

import com.traineemanagement.userservice.model.UserStatus;
import lombok.Data;

import java.util.Set;

@Data
public class AdminDTO {
    // Fields from BaseUser
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String picture;
    private UserStatus status;
    private Set<String> roles; // Assuming we want to send role names rather than full Role objects
}
