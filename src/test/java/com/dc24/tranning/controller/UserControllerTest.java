package com.dc24.tranning.controller;

import com.dc24.tranning.entity.RolesEntity;
import com.dc24.tranning.entity.UsersEntity;
import com.dc24.tranning.repository.RoleRepository;
import com.dc24.tranning.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext).apply(springSecurity())
                .build();
        setupUser();
    }

    private void setupUser(){
        UsersEntity user = new UsersEntity();
        user.setEmail("nhatlinh@123.com");
        user.setUsername("nhatlinh123");
        user.setPassword("admin@123");
        RolesEntity roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
    }

    @Test
    @WithUserDetails("nhatlinh123@gmail.com")
    public void getUserFromContext() throws Exception {
        mockMvc.perform(get("/api/v1/auth/signup"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("nhatlinh123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("nhatlinh@123.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("admin@123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_ADMIN"))
                .andDo(print());
    }

}

