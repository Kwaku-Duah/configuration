package com.traineemanagement.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("TRAINER")
public class Trainer extends BaseUser {

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String country;
    private String address;
    private String phoneNumber;

    @Column(name = "specialization_id")
    private Long specializationId; // One specialization ID linked to this user

    @ElementCollection
    @CollectionTable(name = "user_cohort", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "cohort_id")
    private List<Long> cohortId; // List of cohort IDs linked to this user
}