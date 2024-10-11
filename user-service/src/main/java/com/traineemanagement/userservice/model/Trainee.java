package com.traineemanagement.userservice.model;

import com.traineemanagement.userservice.dto.cohortdto.CohortDto;
import com.traineemanagement.userservice.dto.specializationdto.SpecializationDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("TRAINEE")
public class Trainee extends BaseUser {

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String country;
    private String address;
    private String phoneNumber;
    private String university;

    @Column(name = "specialization_id")
    private Long specializationId; // One specialization ID linked to this user

    @Column(name = "cohort_id")
    private Long cohortId; // One cohort ID linked to this user

    @Transient
    private CohortDto cohort;

    @Transient
    private SpecializationDTO specialization;

    private LocalDate enrollmentDate;

    private LocalDate expectedCompletionDate;
}
