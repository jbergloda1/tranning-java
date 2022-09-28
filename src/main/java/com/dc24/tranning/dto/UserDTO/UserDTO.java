package com.dc24.tranning.dto.UserDTO;

import com.dc24.tranning.entity.RolesEntity;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
public class UserDTO {

    private int id;
    private String username;
    private String email;
    private Set<RolesEntity> roles;

}
