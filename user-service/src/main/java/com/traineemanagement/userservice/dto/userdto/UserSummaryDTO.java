package com.traineemanagement.userservice.dto.userdto;

import lombok.Data;

@Data
public class UserSummaryDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private boolean active;
}