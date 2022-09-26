package com.dc24.tranning.dto.UserDTO;

import java.io.Serializable;

public class UserResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String userResponse;

    public UserResponse(String userResponse) {
        this.userResponse = userResponse;
    }

    public String getUser() {
        return this.userResponse;
    }
}
