package com.traineemanagement.userservice.repository;

import com.traineemanagement.userservice.dto.userdto.TraineeDTO;
import com.traineemanagement.userservice.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends BaseUserRepository<Trainee> {
    List<TraineeDTO> findByCohortId(Long cohortId);
    List<TraineeDTO> findBySpecializationId(Long specializationId);
    List<TraineeDTO> findByCohortIdAndSpecializationId(Long cohortId, Long specializationId);
}
