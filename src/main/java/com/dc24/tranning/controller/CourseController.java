package com.dc24.tranning.controller;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = { "http://localhost:8080"})
@RequestMapping("/api/v1/course")
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);
    //POST
    @PostMapping("/create")
    public CoursesEntity addCourse(@Valid @RequestBody CoursesEntity course) {
        return courseService.saveCourse(course);
    }


    //GET
    @GetMapping
    public ResponseEntity<List<CoursesEntity>> getAllCourses() {
        logger.info("process success!!");
        try {
            logger.info("Course Info");
            List<CoursesEntity> course = courseService.getCourses();
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public CoursesEntity findCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }
//    @GetMapping("/listCourseByUsername/{username}")
//    public List<CoursesEntity> findCoursesByUsername(@PathVariable String username) {
//        return courseService.getCoursesForUser(username);
//    }

    //PUT
    @PutMapping("/edit")
    public CoursesEntity updateCourse(@RequestBody CoursesEntity course)
    {
        return courseService.updateCourse(course);
    }


    //DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable int id) {
        return courseService.deleteCourse(id);
    }

}
