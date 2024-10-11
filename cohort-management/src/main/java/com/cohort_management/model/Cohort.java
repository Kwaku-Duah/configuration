package com.cohort_management.model;

import com.cohort_management.Dto.SpecializationDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cohort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cohortName;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy")
    private Date endDate;

    @ElementCollection
    @CollectionTable(name = "cohort_specializations", joinColumns = @JoinColumn(name = "cohort_id"))
    @Column(name = "specialization_id")
    private List<Long> specializationIds; // List of specialization IDs linked to this cohort

    @Transient
    private List<SpecializationDTO> specializations;

}
