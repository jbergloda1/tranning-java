package com.dc24.tranning.repository;


import com.dc24.tranning.entity.CoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CoursesEntity, Integer> {
    CoursesEntity findByName(String name);
//    List<CoursesEntity> findAllByUsername(String username);
    @Query(value = "select * from courses c where c.id = :id", nativeQuery = true)
    CoursesEntity queryEdit(@Param("id") Integer id);
}
