package com.dc24.tranning.dto.JwtDto;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String usernameOrEmail;
    private String password;

    //need default constructor for JSON Parsing
    public JwtRequest()
    {

    }

    public JwtRequest(String usernameOrEmail, String password) {
        this.setUsername(usernameOrEmail);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.usernameOrEmail;
    }

    public void setUsername(String username) {
        this.usernameOrEmail = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
