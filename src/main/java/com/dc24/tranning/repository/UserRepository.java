package com.dc24.tranning.repository;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByEmail(String email);
    Optional<UsersEntity> findByUsernameOrEmail(String username, String email);
    Optional<UsersEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "select * from users u where u.id = :id", nativeQuery = true)
    UsersEntity queryEdit(@Param("id") Integer id);

}
