package com.traineemanagement.userservice.dto.userdto;

import com.traineemanagement.userservice.dto.cohortdto.CohortDto;
import com.traineemanagement.userservice.dto.specializationdto.SpecializationDTO;
import com.traineemanagement.userservice.model.Gender;
import com.traineemanagement.userservice.model.RoleType;
import com.traineemanagement.userservice.model.UserStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TraineeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String country;
    private String address;
    private String phoneNumber;
    private String university;
    private SpecializationDTO specialization;
    //private String specializationName;
    private CohortDto cohort;
    //private String cohortName;
    private UserStatus status;
    private LocalDate enrollmentDate;
    private LocalDate expectedCompletionDate;
    private RoleType roles;
}
