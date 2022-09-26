package com.dc24.tranning.Service;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.repository.CourseRepository;
import com.dc24.tranning.service.CourseService;
import org.mockito.Mockito;

public class CourseServiceTest {
    private CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
    private CourseService courseService = CoursesEntity(courseRepository);
}
