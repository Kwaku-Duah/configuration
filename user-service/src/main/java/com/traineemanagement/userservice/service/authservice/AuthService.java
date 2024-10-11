package com.traineemanagement.userservice.service.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.traineemanagement.userservice.Util.JwtUtil;
import com.traineemanagement.userservice.dto.userdto.LoginResponseDto;
import com.traineemanagement.userservice.dto.userdto.TokenDto;
import com.traineemanagement.userservice.exception.WrongCredentialsException;
import com.traineemanagement.userservice.model.BaseUser;
import com.traineemanagement.userservice.model.UserStatus;
import com.traineemanagement.userservice.service.userservice.CentralUserService;


@Service
public class AuthService {


     @Autowired
    private CentralUserService centralUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtService;

    

   public LoginResponseDto login(String email, String password) {
        BaseUser user = centralUserService.findByEmail(email)
                .orElseThrow(() -> new WrongCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongCredentialsException("Invalid email or password");
        }

        String roleAsString = user.getRoles().name();
        String token = jwtService.generateToken(user.getId(), roleAsString);

        // Create and return LoginResponseDto
        return new LoginResponseDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            roleAsString,
            user.getStatus().equals(UserStatus.ACTIVE),
            token,
           jwtService.getJwtExpirationInMs()

        );
    }


    public void validateToken(String token) {
        jwtService.validateToken(token);  
    }

    public TokenDto generateToken(String email) {
        BaseUser user = centralUserService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String roleAsString = user.getRoles().name();
        String token = jwtService.generateToken(user.getId(), roleAsString);

        return new TokenDto(token);
    }
}
