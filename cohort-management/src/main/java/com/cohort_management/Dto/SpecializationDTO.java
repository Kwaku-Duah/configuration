package com.cohort_management.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecializationDTO {
    private Long id;
    private String type;
    private String description;
    private String prerequisites;
}
