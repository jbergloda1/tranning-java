package com.dc24.tranning.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
