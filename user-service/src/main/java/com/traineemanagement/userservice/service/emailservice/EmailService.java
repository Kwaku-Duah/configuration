package com.traineemanagement.userservice.service.emailservice;

import com.traineemanagement.userservice.model.BaseUser;
import com.traineemanagement.userservice.service.passwordservice.PasswordService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;


    public EmailService(JavaMailSender mailSender, PasswordService passwordGeneratorService) {
        this.mailSender = mailSender;
    }

    public void sendTraineeEmail(BaseUser user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Training Program");
        message.setText("""
                Hello %s,
                Congratulations! You have been admitted to our training.
                Tap on the link to reset your password and access your portal.
                Use %s as your default password.
                """.formatted(user.getEmail(), user.getPassword()));
        mailSender.send(message);
    }

    public void sendTrainerEmail(BaseUser user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Training Program");
        message.setText("""
                Hello %s,
                Your instructor account is ready. You will be handling %s trainees.
                Use %s as your default password.
                Tap on this link to change your password.
                """.formatted(user.getEmail() /*user.getSpecialization*/ ,user.getPassword()));
        mailSender.send(message);
    }

}
