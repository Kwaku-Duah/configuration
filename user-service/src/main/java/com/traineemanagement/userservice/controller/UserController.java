package com.traineemanagement.userservice.controller;

import com.traineemanagement.userservice.dto.userdto.TraineeDTO;
import com.traineemanagement.userservice.dto.userdto.TrainerDTO;
import com.traineemanagement.userservice.model.Trainee;
import com.traineemanagement.userservice.service.userservice.UserService;
import com.traineemanagement.userservice.dto.userdto.PasswordChangeRequest;
import com.traineemanagement.userservice.service.passwordservice.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/auth")
//@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final PasswordService passwordService;

    public UserController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    // Trainee endpoints
    @PostMapping("/trainees")
    public ResponseEntity<TraineeDTO> createTrainee(@RequestBody Trainee trainee) {
        return ResponseEntity.ok(userService.createTrainee(trainee));
    }

    @PostMapping("/trainees/changePassword")
    public ResponseEntity<?> changeTraineePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        return ResponseEntity.ok(passwordService.changeTraineePassword(passwordChangeRequest));
    }


    @GetMapping("/trainees/getAllTrainees")
    public ResponseEntity<List<TraineeDTO>> getAllTrainees() {
        return ResponseEntity.ok(userService.getAllTrainees());
    }

    @GetMapping("/trainees/{id}")
    public ResponseEntity<TraineeDTO> getTraineeById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTraineeById(id));
    }

    @PutMapping("/trainees/{id}")
    public ResponseEntity<TraineeDTO> updateTrainee(@PathVariable Long id, @RequestBody TraineeDTO traineeDTO) {
        return ResponseEntity.ok(userService.updateTrainee(id, traineeDTO));
    }


    // Trainer endpoints
    @PostMapping("/trainers/createTrainer")
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO trainerDTO) {
        return ResponseEntity.ok(userService.createTrainer(trainerDTO));
    }

    @PostMapping("/trainers/changePassword")
    public ResponseEntity<?> changeTrainerPassword(PasswordChangeRequest passwordChangeRequest){
        return ResponseEntity.ok(passwordService.changeTrainerPassword(passwordChangeRequest));
    }

    @GetMapping("/trainers/getAllTrainers")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(userService.getAllTrainers());
    }

    @GetMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTrainerById(id));
    }

    @PutMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable Long id, @RequestBody TrainerDTO trainerDTO) {
        return ResponseEntity.ok(userService.updateTrainer(id, trainerDTO));
    }
    @PostMapping("/{id}/assign-cohort/{cohortId}")
    public  ResponseEntity<String> assignCohort(@PathVariable Long id, @PathVariable Long cohortId) {
        try{
            userService.assignTrainerToCohort(id,cohortId);
            return ResponseEntity.ok("Trainer assigned to cohort successfully");
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }


    // Endpoint to get trainees by cohortId and specializationId
    @GetMapping("/trainees/cohort/{cohortId}/specialization/{specializationId}")
    public ResponseEntity<List<TraineeDTO>> getTraineesByCohortAndSpecialization(@PathVariable Long cohortId,
                                                              @PathVariable Long specializationId) {
        return ResponseEntity.ok(userService.getTraineesByCohortAndSpecialization(cohortId, specializationId));
    }

    // Endpoint to get trainees by cohortId
    @GetMapping("/trainees/cohort/{cohortId}")
    public ResponseEntity<List<TraineeDTO>> getTraineesByCohort(@PathVariable Long cohortId) {
        return ResponseEntity.ok(userService.getTraineesByCohort(cohortId));
    }

    // Endpoint to get trainees by specializationId
    @GetMapping("/trainees/specialization/{specializationId}")
    public ResponseEntity<List<TraineeDTO>> getTraineesBySpecialization(@PathVariable Long specializationId) {
        return ResponseEntity.ok(userService.getTraineesBySpecializationId(specializationId));
    }

    // Endpoint to get trainers by cohortId
    @GetMapping("/trainers/cohort/{cohortId}")
    public ResponseEntity<List<TrainerDTO>> getTrainersByCohort(@PathVariable Long cohortId) {
        return ResponseEntity.ok(userService.getTrainersByCohortId(cohortId));
    }

    // Endpoint to get trainers by specializationId
    @GetMapping("/trainers/specialization/{specializationId}")
    public ResponseEntity<List<TrainerDTO>> getTrainersBySpecialization(@PathVariable Long specializationId) {
        return ResponseEntity.ok(userService.getTrainersBySpecializationId(specializationId));
    }

    // Endpoint to get trainers by cohortId and specializationId
    @GetMapping("/trainers/cohort/{cohortId}/specialization/{specializationId}")
    public ResponseEntity<List<TrainerDTO>> getTrainersByCohortAndSpecialization(@PathVariable Long cohortId,
                                                              @PathVariable Long specializationId) {
        return ResponseEntity.ok(userService.getTrainersByCohortIdAndSpecializationId(cohortId, specializationId));
    }
}