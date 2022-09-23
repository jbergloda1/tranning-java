package com.dc24.tranning.Service;

import com.dc24.tranning.service.TokenAuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenAuthenticationServiceTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/course")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String token = TokenAuthenticationService.createToken("nhatlinh1234");

        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("/course").header("Authorization", token))
                .andExpect(status().isUnauthorized());
    }

}
