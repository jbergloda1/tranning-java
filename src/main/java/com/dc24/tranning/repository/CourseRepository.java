package com.dc24.tranning.repository;


import com.dc24.tranning.entity.CoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CoursesEntity, Integer> {
    CoursesEntity findByName(String name);
//    List<CoursesEntity> findAllByUsername(String username);
}
