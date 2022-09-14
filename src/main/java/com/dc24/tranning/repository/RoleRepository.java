package com.dc24.tranning.repository;

import com.dc24.tranning.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByName(String name);
}
