package com.example.securityDemo.controller;

import com.example.securityDemo.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityConfig securityConfig;

    @Test
    @WithMockUser
    void runEndpointReturnsRunning() throws Exception {
        mockMvc.perform(get("/demo/run"))
                .andExpect(status().isOk())
                .andExpect(content().string("Running"));
    }

    @Test
    @WithMockUser
    void runEndpointReturnsNotFoundForInvalidPath() throws Exception {
        mockMvc.perform(get("/demo/invalid"))
                .andExpect(status().isNotFound());
    }
}