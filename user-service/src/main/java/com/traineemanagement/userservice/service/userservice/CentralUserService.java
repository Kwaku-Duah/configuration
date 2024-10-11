package com.traineemanagement.userservice.service.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traineemanagement.userservice.model.BaseUser;
import com.traineemanagement.userservice.repository.TraineeRepository;
import com.traineemanagement.userservice.repository.TrainerRepository;
import com.traineemanagement.userservice.repository.AdminRepository;

import java.util.Optional;

@Service
public class CentralUserService {

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Optional<BaseUser> findByEmail(String email) {
        // Check in each repository
        Optional<BaseUser> trainee = traineeRepository.findByEmail(email)
                .map(user -> (BaseUser) user);
        if (trainee.isPresent()) {
            return trainee;
        }
        
        Optional<BaseUser> trainer = trainerRepository.findByEmail(email)
                .map(user -> (BaseUser) user);
        if (trainer.isPresent()) {
            return trainer;
        }

        return adminRepository.findByEmail(email)
                .map(user -> (BaseUser) user);
    }

    public Optional<BaseUser> findById(Long id) {
        // Check in each repository
        Optional<BaseUser> trainee = traineeRepository.findById(id)
                .map(user -> (BaseUser) user);
        if (trainee.isPresent()) {
            return trainee;
        }

        Optional<BaseUser> trainer = trainerRepository.findById(id)
                .map(user -> (BaseUser) user);
        if (trainer.isPresent()) {
            return trainer;
        }

        return adminRepository.findById(id)
                .map(user -> (BaseUser) user);
    }

    public boolean existsByEmail(String email) {
        // Check in each repository
        return traineeRepository.existsByEmail(email) ||
               trainerRepository.existsByEmail(email) ||
               adminRepository.existsByEmail(email);
    }
    
    public Optional<BaseUser> findByPassword(String password) {
        // Check in each repository
        Optional<BaseUser> trainee = traineeRepository.findByPassword(password)
                .map(user -> (BaseUser) user);
        if (trainee.isPresent()) {
            return trainee;
        }

        Optional<BaseUser> trainer = trainerRepository.findByPassword(password)
                .map(user -> (BaseUser) user);
        if (trainer.isPresent()) {
            return trainer;
        }

        return adminRepository.findByPassword(password)
                .map(user -> (BaseUser) user);
    }
}
