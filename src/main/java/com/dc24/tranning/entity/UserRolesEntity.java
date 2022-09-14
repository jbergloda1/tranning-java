package com.dc24.tranning.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRolesEntity {
    @Id
    @Column(name = "role_id")
    private int role_id;
    @Column(name = "user_id")
    private int user_id;
    @Column(name = "grant_date")
    private Timestamp grant_date;
}