package com.traineemanagement.userservice.repository;

import com.traineemanagement.userservice.dto.userdto.TrainerDTO;
import com.traineemanagement.userservice.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends BaseUserRepository<Trainer> {
    List<TrainerDTO> findByCohortId(List<Long> cohortId);
    List<TrainerDTO> findBySpecializationId(Long SpecializationId);
    List<TrainerDTO> findBySpecializationIdAndCohortId(Long specializationId, List<Long> cohortId);
}
