package com.traineemanagement.userservice.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    private String defaultPassword;
    private String newPassword1;
    private String newPassword2;

}
