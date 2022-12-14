package com.dc24.tranning.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Document(indexName = "usersindex")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String username;
    @Column(name = "email")
    @Length(min = 5, message = "*Your email must have at least 5 characters")
    @NotEmpty(message = "*Please provide a email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "website")
    private String website;

    @Column(name = "language")
    private String language;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "company")
    private String company;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "enabled")
    private Boolean enabled;




    public UsersEntity() {

    }


    public void User() {

    }


    public void UserResponses(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
