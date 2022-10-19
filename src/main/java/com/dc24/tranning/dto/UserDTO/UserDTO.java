package com.dc24.tranning.dto.UserDTO;

import com.dc24.tranning.entity.RolesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
public class UserDTO {

    private int id;
    private String username;
    private String email;
    private String name;
    private String company;
    private String phone;
    private String country;
    private String website;
    private String language;
    private Date birthday;
    private String gender;
    private String introduction;
    private Set<RolesEntity> roles;

}
