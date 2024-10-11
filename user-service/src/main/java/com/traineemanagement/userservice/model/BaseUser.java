package com.traineemanagement.userservice.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String picture;

    @Enumerated(EnumType.STRING)
    private RoleType roles;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
