package com.dc24.tranning.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String token;
    private String createPassword;
    private String confirmPassword;
}
