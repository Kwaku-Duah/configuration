package com.specialization_management.service;

import com.specialization_management.model.Specialization;
import com.specialization_management.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final CohortClient cohortClient;

    public SpecializationService(SpecializationRepository specializationRepository, CohortClient cohortClient) {
        this.specializationRepository = specializationRepository;
        this.cohortClient = cohortClient;
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    public Optional<Specialization> getSpecializationById(Long id) {
        return specializationRepository.findById(id);
    }

    @Transactional
    public Specialization createSpecialization(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Transactional
    public Specialization updateSpecialization(Long id, Specialization specialization) {
        Specialization spec = specializationRepository.findById(id).orElseThrow(() -> new RuntimeException("Specialization not found"));
        spec.setType(specialization.getType());
        spec.setDescription(specialization.getDescription());
        spec.setPrerequisites(specialization.getPrerequisites());
        return specializationRepository.save(spec);
    }

    public String deleteSpecialization(Long id) {
        specializationRepository.deleteById(id);
        return "Specialization deleted successfully";
    }

    public Boolean isCohortValid(Long cohortId) {
        return cohortClient.isCohortExists(cohortId);
    }
}
