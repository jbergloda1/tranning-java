package com.dc24.tranning.repository;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByEmail(String email);
    Optional<UsersEntity> findByUsernameOrEmail(String username, String email);
    Optional<UsersEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "select * from users u where u.id = :id", nativeQuery = true)
    UsersEntity findUserById(@Param("id") Integer id);

    @Query(value = "select * from users u where u.email = :email", nativeQuery = true)
    UsersEntity findUserByEmail(@Param("email") String email);

    UsersEntity findByResetPasswordToken(String token);

    @Query(value = "select * from users u where u.verification_code = ?1", nativeQuery = true)
    UsersEntity findByVerificationCode(String code);

    @Query(value  = "select * from users u where u.username = :username", nativeQuery = true)
    UsersEntity checkEnabled(@Param("username") String username);


}
