package com.dc24.tranning.repository;

import com.dc24.tranning.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByUsernameAndPassword(String username, String password);
    UsersEntity findTopByUsername(String username);
    UsersEntity findTopByPassword(String password);
}
