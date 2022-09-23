package com.dc24.tranning.controller;

import com.dc24.tranning.entity.CoursesEntity;
import com.dc24.tranning.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@Qualifier
public class CourseControllerTest {
    @Autowired
    private CourseController courseController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;

    private List<CoursesEntity> courses;

    @Test
    @WithMockUser(value = "nhatlinh123", roles = {"ROLE_ADMIN"})
    public void getAllCourses() throws Exception {
//        CoursesEntity course = new CoursesEntity();
//        course.setName("java");
//        course.setDescription("java");
//        course.setStatus("java");
//
//        when(courseService.getCourseById(anyInt())).thenReturn(course);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("api/v1/course/60"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("java"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("java"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("java"))
//                .andExpect(status().isOk());
        given(courseService.getCourses()).willReturn(courses);
        mockMvc.perform(get("api/v1/course")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$" , hasSize(2)));

    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}