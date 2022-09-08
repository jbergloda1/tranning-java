package com.dc24.tranning.controller;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);
    //POST
    @PostMapping("/addCourse")
    public CoursesEntity addCourse(@RequestBody CoursesEntity course) {
        return courseService.saveCourse(course);
    }

    @PostMapping("/addCourses")
    public List<CoursesEntity> addCourses(@RequestBody List<CoursesEntity> courses) {
        return courseService.saveCourses(courses);
    }

    //GET
    @GetMapping("/courses")
    public List<CoursesEntity> getAllCourses() {
        return courseService.getCourses();
    }
    @GetMapping("/coursesById/{id}")
    public CoursesEntity findCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }
    @GetMapping("/coursesByName/{name}")
    public CoursesEntity findCourseByName(@PathVariable String name) {
        return courseService.getCourseByName(name);
    }
//    @GetMapping("/listCourseByUsername/{username}")
//    public List<CoursesEntity> findCoursesByUsername(@PathVariable String username) {
//        return courseService.getCoursesForUser(username);
//    }

    //PUT
    @PutMapping("/coursesUpdate")
    public CoursesEntity updateCourse(@RequestBody CoursesEntity course)
    {
        return courseService.updateCourse(course);
    }


    //DELETE
    @DeleteMapping("/coursesDelete/{id}")
    public String deleteCourse(@PathVariable int id) {
        return courseService.deleteCourse(id);
    }

}
