package com.cohort_management.service;

import com.cohort_management.Dto.CohortDto;
import com.cohort_management.Dto.SpecializationDTO;
import com.cohort_management.Repository.CohortRepository;
import com.cohort_management.model.Cohort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CohortService {
    private final CohortRepository cohortRepository;
    private final SpecializationClient specializationClient;

    public CohortService(CohortRepository cohortRepository, SpecializationClient specializationClient) {
        this.cohortRepository = cohortRepository;
        this.specializationClient = specializationClient;
    }

    public List<SpecializationDTO> getSpecializationsForCohort(Long cohortId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new IllegalArgumentException("Cohort not found"));

        // Use Feign client to get all specialization details
        return specializationClient.getAllSpecializations();
    }

    public CohortDto createCohort(Cohort cohort) {
        // Check if specializationIds is null or empty
        if (cohort.getSpecializationIds() == null || cohort.getSpecializationIds().isEmpty()) {
            throw new IllegalArgumentException("Specialization IDs cannot be null or empty.");
        }

        // Validate and fetch specializations from the client
        List<SpecializationDTO> validSpecializations = cohort.getSpecializationIds().stream()
                .map(specializationId -> {
                    SpecializationDTO specializationResponse = specializationClient.getSpecializationById(specializationId);

                    // Check if specialization exists, throw an exception if not found
                    if (specializationResponse == null || specializationResponse.getId() == null) {
                        throw new IllegalArgumentException("Specialization with ID " + specializationId + " does not exist.");
                    }

                    // Map SpecializationResponse to Specialization entity if needed
                    SpecializationDTO specialization = new SpecializationDTO();
                    specialization.setId(specializationResponse.getId());
                    specialization.setType(specializationResponse.getType());
                    specialization.setDescription(specializationResponse.getDescription());
                    specialization.setPrerequisites(specializationResponse.getPrerequisites());

                    return specialization;  // Return the valid specialization
                })
                .collect(Collectors.toList());

        // Set the valid specializations in the cohort object for persistence
        cohort.setSpecializations(validSpecializations);

        // Return the cohort with fully populated specializations
        Cohort savedCohort = cohortRepository.save(cohort);
        CohortDto cohortDto = new CohortDto();
        cohortDto.setCohortName(savedCohort.getCohortName());
        cohortDto.setId(savedCohort.getId());
        cohortDto.setDescription(savedCohort.getDescription());
        cohortDto.setStartDate(savedCohort.getStartDate());
        cohortDto.setEndDate(savedCohort.getEndDate());
        cohortDto.setSpecializations((List<SpecializationDTO>) savedCohort.getSpecializations());
        return cohortDto;
    }






    public List<Cohort> getAllCohorts() {
        return cohortRepository.findAll();
    }

    public Optional<Cohort> getCohortById(Long cohortId) {
        Optional<Cohort> cohortOptional = cohortRepository.findById(cohortId);

        if (cohortOptional.isPresent()) {
            return cohortOptional;
        } else {
            throw new IllegalArgumentException("Cohort not found with ID: " + cohortId);
        }
    }

    public Cohort updateCohort(Long cohortId, Cohort cohort) {
        Optional<Cohort> cohortOptional = cohortRepository.findById(cohortId);
        if (cohortOptional.isPresent()) {
            Cohort existingCohort = cohortOptional.get();
            existingCohort.setCohortName(cohort.getCohortName());
            existingCohort.setDescription(cohort.getDescription());
            existingCohort.setStartDate(cohort.getStartDate());
            existingCohort.setEndDate(cohort.getEndDate());
            existingCohort.setSpecializationIds(cohort.getSpecializationIds());

            return cohortRepository.save(existingCohort);
        } else {
            throw new IllegalArgumentException("cohort not found with ID: " + cohortId);
        }
    }

    public void deleteCohortById(Long cohortId) {
        Optional<Cohort> cohortOptional = cohortRepository.findById(cohortId);
        if (cohortOptional.isPresent()) {
            cohortRepository.delete(cohortOptional.get());
        } else {
            throw new IllegalArgumentException("Cohort not found with ID: " + cohortId);
        }
    }
}
