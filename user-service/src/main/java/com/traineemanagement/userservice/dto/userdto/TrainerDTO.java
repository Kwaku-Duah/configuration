package com.traineemanagement.userservice.dto.userdto;

import com.traineemanagement.userservice.model.Gender;
import com.traineemanagement.userservice.model.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TrainerDTO {
    // Fields from BaseUser
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String picture;
    private UserStatus status;
    private Set<String> roles;

    // Fields specific to Trainer
    private LocalDate dateOfBirth;
    private Gender gender;
    private String country;
    private String address;
    private String phoneNumber;
    private Long specializationId;
    private String specializationName; // This will be populated when converting from entity to DTO
}
