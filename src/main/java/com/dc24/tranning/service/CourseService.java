package com.dc24.tranning.service;

import com.dc24.tranning.controller.CourseController;
import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);
    @Autowired
    private CourseRepository courseRepository;

    //POST
    public CoursesEntity saveCourse(CoursesEntity course) {
        return courseRepository.save(course);
    }

    //Optional!
    public List<CoursesEntity> saveCourses(List<CoursesEntity> courses) {
        return courseRepository.saveAll(courses);
    }

    //GET
    public List<CoursesEntity> getCourses() {
        return courseRepository.findAll();
    }
    public CoursesEntity getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }
    public CoursesEntity getCourseByName(String name) {
        return courseRepository.findByName(name);
    }
//    public List<CoursesEntity> getCoursesForUser(String username) {
//        return courseRepository.findAllByUsername(username);
//    }

    //PUT
    public CoursesEntity updateCourse(CoursesEntity course) {
        CoursesEntity existing_course = courseRepository.queryEdit(course.getId());
        existing_course.setName(course.getName());
        existing_course.setDescription(course.getDescription());
        existing_course.setStatus(course.getStatus());
        return courseRepository.save(existing_course);
    }

    //DELETE
    public String deleteCourse(int id) {
        courseRepository.deleteById(id);
        return id + " id -> course removed/completed";
    }

}
