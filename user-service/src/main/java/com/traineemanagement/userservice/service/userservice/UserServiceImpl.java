package com.traineemanagement.userservice.service.userservice;

import com.traineemanagement.userservice.dto.cohortdto.CohortDto;
import com.traineemanagement.userservice.dto.specializationdto.SpecializationDTO;
import com.traineemanagement.userservice.dto.userdto.*;
import com.traineemanagement.userservice.model.*;
import com.traineemanagement.userservice.repository.*;
import com.traineemanagement.userservice.service.passwordservice.PasswordService;
import com.traineemanagement.userservice.exception.ResourceNotFoundException;
import com.traineemanagement.userservice.service.incommunicationservice.CohortClient;
import com.traineemanagement.userservice.service.incommunicationservice.SpecializationClient;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.traineemanagement.userservice.service.emailservice.EmailService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpecializationClient specializationClient;
    private final CohortClient cohortClient;
    private final PasswordService passwordGeneratorService;
    private final EmailService emailService;

    public UserServiceImpl(TraineeRepository traineeRepository, TrainerRepository trainerRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, SpecializationClient specializationClient,
            CohortClient cohortClient, PasswordService passwordGeneratorService, EmailService emailService) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.passwordEncoder = passwordEncoder;
        this.specializationClient = specializationClient;
        this.cohortClient = cohortClient;
        this.passwordGeneratorService = passwordGeneratorService;
        this.emailService = emailService;
    }

    @Override
    public TraineeDTO createTrainee(Trainee trainee) {
        if (trainee.getCohortId() == null || trainee.getCohortId() == 0) {
            throw new IllegalArgumentException("Cohort id cannot be empty");
        }
        if (trainee.getSpecializationId() == null || trainee.getSpecializationId() == 0) {
            throw new IllegalArgumentException("Specialization id cannot be empty");
        }

        SpecializationDTO specializationDTO = specializationClient.getSpecializationById(trainee.getSpecializationId());
        if (specializationDTO == null) {
            throw new IllegalArgumentException("Specialization not found");
        }

        CohortDto cohortDTO = cohortClient.getCohortById(trainee.getCohortId());
        if (cohortDTO == null) {
            throw new IllegalArgumentException("Cohort not found");
        }

 
        String generatedPassword = passwordGeneratorService.randomDefaultPassword();

        trainee.setPassword(passwordEncoder.encode(generatedPassword));

        trainee.setStatus(UserStatus.ACTIVE);

        Trainee savedTrainee = traineeRepository.save(trainee);
        emailService.sendTraineeEmail(savedTrainee);

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setId(savedTrainee.getId());
        traineeDTO.setFirstName(savedTrainee.getFirstName());
        traineeDTO.setLastName(savedTrainee.getLastName());
        traineeDTO.setEmail(savedTrainee.getEmail());
        traineeDTO.setPhoneNumber(savedTrainee.getPhoneNumber());
        traineeDTO.setPassword(passwordEncoder.encode(trainee.getPassword()));
        traineeDTO.setCountry(savedTrainee.getCountry());
        traineeDTO.setGender(savedTrainee.getGender());
        traineeDTO.setStatus(savedTrainee.getStatus());
        traineeDTO.setEnrollmentDate(savedTrainee.getEnrollmentDate());
        traineeDTO.setExpectedCompletionDate(savedTrainee.getExpectedCompletionDate());
        traineeDTO.setDateOfBirth(savedTrainee.getDateOfBirth());
        traineeDTO.setAddress(savedTrainee.getAddress());
        traineeDTO.setUniversity(savedTrainee.getUniversity());
        traineeDTO.setRoles(savedTrainee.getRoles());
        traineeDTO.setCohort(cohortDTO);
        traineeDTO.setSpecialization(specializationDTO);
        return traineeDTO;
    }

    @Override
    public List<TraineeDTO> getAllTrainees() {
        return traineeRepository.findAll().stream()
                .map(this::convertToTraineeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TraineeDTO getTraineeById(Long id) {
        return traineeRepository.findById(id)
                .map(this::convertToTraineeDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found with id: " + id));
    }

    @Override
    public TraineeDTO updateTrainee(Long id, TraineeDTO traineeDTO) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found with id: " + id));
        BeanUtils.copyProperties(traineeDTO, trainee, "id", "password", "roles", "status");
        return convertToTraineeDTO(traineeRepository.save(trainee));
    }

    @Override
    public TrainerDTO createTrainer(TrainerDTO trainerDTO) {
        Trainer trainer = new Trainer();
        BeanUtils.copyProperties(trainerDTO, trainer);
        trainer.setPassword(passwordGeneratorService.randomDefaultPassword());
        trainer.setStatus(UserStatus.ACTIVE);
        // Role trainerRole = roleRepository.findByName(RoleType.ROLE_TRAINER)
        // .orElseThrow(() -> new RuntimeException("Trainer role not found"));
        // trainer.setRoles(Set.of(trainerRole));
        // Fetch specialization details using Feign client and check if it exists
        SpecializationDTO specializationDTO = specializationClient
                .getSpecializationById(trainerDTO.getSpecializationId());
        if (specializationDTO == null) {
            throw new IllegalArgumentException("Specialization not found with ID: " + trainerDTO.getSpecializationId());
        }

        // Set the validated specialization ID
        trainer.setSpecializationId(trainerDTO.getSpecializationId());
        Trainer savedTrainer = trainerRepository.save(trainer);
        emailService.sendTrainerEmail(savedTrainer);

        return convertToTrainerDTO(savedTrainer);
    }

    @Override
    public List<TrainerDTO> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(this::convertToTrainerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrainerDTO getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .map(this::convertToTrainerDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
    }

    @Override
    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
        BeanUtils.copyProperties(trainerDTO, trainer, "id", "password", "roles", "status");
        return convertToTrainerDTO(trainerRepository.save(trainer));
    }

    @Override
    public List<UserSummaryDTO> getAllUsers() {
        List<UserSummaryDTO> users = traineeRepository.findAll().stream()
                .map(this::convertToUserSummaryDTO)
                .collect(Collectors.toList());
        users.addAll(trainerRepository.findAll().stream()
                .map(this::convertToUserSummaryDTO)
                .collect(Collectors.toList()));
        return users;
    }

    @Override
    public void deactivateUser(Long id) {
        Trainee trainee = traineeRepository.findById(id).orElse(null);
        if (trainee != null) {
            trainee.setStatus(UserStatus.INACTIVE);
            traineeRepository.save(trainee);
            return;
        }

        Trainer trainer = trainerRepository.findById(id).orElse(null);
        if (trainer != null) {
            trainer.setStatus(UserStatus.INACTIVE);
            trainerRepository.save(trainer);
            return;
        }

        throw new ResourceNotFoundException("User not found with id: " + id);
    }

    private TraineeDTO convertToTraineeDTO(Trainee trainee) {
        TraineeDTO dto = new TraineeDTO();
        BeanUtils.copyProperties(trainee, dto);
        // dto.setRoles(trainee.getRoles().stream().map(role ->
        // role.getName().name()).collect(Collectors.toSet()));
        return dto;
    }

    private TrainerDTO convertToTrainerDTO(Trainer trainer) {
        TrainerDTO dto = new TrainerDTO();
        BeanUtils.copyProperties(trainer, dto);
        // dto.setRoles(trainer.getRoles().stream().map(role ->
        // role.getName().name()).collect(Collectors.toSet()));
        return dto;
    }

    private UserSummaryDTO convertToUserSummaryDTO(BaseUser user) {
        UserSummaryDTO dto = new UserSummaryDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        // dto.setRole(user.getRoles().iterator().next().getName().name());
        dto.setActive(user.getStatus() == UserStatus.ACTIVE);
        return dto;
    }

    public void assignTrainerToCohort(Long id, Long cohortId) {
        CohortDto cohort = cohortClient.getCohortById(cohortId);
        if (cohort == null) {
            throw new IllegalArgumentException("Cohort not found with id: " + cohortId);
        }
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.setCohortId(Collections.singletonList(cohortId));
        trainerRepository.save(trainer);
    }

    public List<TraineeDTO> getTraineesByCohortAndSpecialization(Long cohortId, Long specializationId) {
        return traineeRepository.findByCohortIdAndSpecializationId(cohortId, specializationId);
    }

    public List<TraineeDTO> getTraineesByCohort(Long cohortId) {
        return traineeRepository.findByCohortId(cohortId);
    }

    public List<TraineeDTO> getTraineesBySpecializationId(Long specializationId) {
        return traineeRepository.findBySpecializationId(specializationId);
    }

    public List<TrainerDTO> getTrainersByCohortId(Long cohortId) {
        return trainerRepository.findByCohortId(Collections.singletonList(cohortId));
    }

    public List<TrainerDTO> getTrainersBySpecializationId(Long specializationId) {
        return trainerRepository.findBySpecializationId(specializationId);
    }

    public List<TrainerDTO> getTrainersByCohortIdAndSpecializationId(Long cohortId, Long specializationId) {
        return trainerRepository.findBySpecializationIdAndCohortId(specializationId,
                Collections.singletonList(cohortId));
    }
}