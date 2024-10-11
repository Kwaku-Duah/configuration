package com.traineemanagement.userservice.service.passwordservice;
import com.traineemanagement.userservice.dto.userdto.PasswordChangeRequest;
import com.traineemanagement.userservice.exception.DefaultPasswordException;
import com.traineemanagement.userservice.model.Trainee;
import com.traineemanagement.userservice.model.Trainer;
import com.traineemanagement.userservice.repository.TraineeRepository;
import com.traineemanagement.userservice.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {
    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    TraineeRepository traineeRepository;

    // Generate a random default password
    public String randomDefaultPassword() {
            return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        }

    // Generate a password reset code for email
    public String randomResetCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    public String changeTrainerPassword(PasswordChangeRequest passwordChangeRequest) {
        try {
            Trainer trainer = trainerRepository
                    .findByPassword(passwordChangeRequest.getDefaultPassword()).orElseThrow(() -> (new DefaultPasswordException("wrong default password")));
            trainer.setPassword(passwordChangeRequest.getNewPassword2());
            trainerRepository.save(trainer);
            return "Password changed successfully";
        } catch (Exception e) {
            throw new DefaultPasswordException("wrong default password" + e.getMessage());
        }
    }

    public String changeTraineePassword(PasswordChangeRequest passwordChangeRequest) {
        try {
            Trainee trainee = traineeRepository
                    .findByPassword(passwordChangeRequest.getDefaultPassword()).orElseThrow(() -> (new DefaultPasswordException("wrong default password")));
            trainee.setPassword(passwordChangeRequest.getNewPassword2());
            traineeRepository.save(trainee);
            return "Password changed successfully";
        } catch (Exception e) {
            throw new DefaultPasswordException("wrong default password" + e.getMessage());
        }
    }

}
