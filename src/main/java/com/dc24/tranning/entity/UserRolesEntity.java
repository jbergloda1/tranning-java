package com.dc24.tranning.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_roles", schema = "public", catalog = "postgres")
@IdClass(UserRolesEntityPK.class)
public class UserRolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id")
    private int roleId;
    @Basic
    @Column(name = "grant_date")
    private Timestamp grantDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Timestamp getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Timestamp grantDate) {
        this.grantDate = grantDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRolesEntity that = (UserRolesEntity) o;

        if (userId != that.userId) return false;
        if (roleId != that.roleId) return false;
        if (grantDate != null ? !grantDate.equals(that.grantDate) : that.grantDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + roleId;
        result = 31 * result + (grantDate != null ? grantDate.hashCode() : 0);
        return result;
    }
}
