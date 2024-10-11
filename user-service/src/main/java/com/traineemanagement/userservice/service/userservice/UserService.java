package com.traineemanagement.userservice.service.userservice;

import com.traineemanagement.userservice.dto.userdto.TraineeDTO;
import com.traineemanagement.userservice.dto.userdto.TrainerDTO;
import com.traineemanagement.userservice.dto.userdto.UserSummaryDTO;
import com.traineemanagement.userservice.model.Trainee;

import java.util.List;

public interface UserService {
    //TraineeDTO createTrainee(TraineeDTO traineeDTO);
    TraineeDTO createTrainee(Trainee trainee);
    List<TraineeDTO> getAllTrainees();
    TraineeDTO getTraineeById(Long id);
    TraineeDTO updateTrainee(Long id, TraineeDTO traineeDTO);

    TrainerDTO createTrainer(TrainerDTO trainerDTO);
    List<TrainerDTO> getAllTrainers();
    TrainerDTO getTrainerById(Long id);
    TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO);

    List<UserSummaryDTO> getAllUsers();
    void deactivateUser(Long id);

    List<TraineeDTO> getTraineesByCohortAndSpecialization(Long cohortId, Long specializationId);

    List<TraineeDTO> getTraineesByCohort(Long cohortId);

    List<TrainerDTO> getTrainersBySpecializationId(Long specializationId);

    List<TraineeDTO> getTraineesBySpecializationId(Long specializationId);

    List<TrainerDTO> getTrainersByCohortId(Long cohortId);

    List<TrainerDTO> getTrainersByCohortIdAndSpecializationId(Long cohortId, Long specializationId);

    void assignTrainerToCohort(Long id, Long cohortId);
}
