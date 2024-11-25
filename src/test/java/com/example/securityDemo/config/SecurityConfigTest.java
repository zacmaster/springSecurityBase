package com.example.securityDemo.config;

import com.example.securityDemo.controller.DemoControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoControllerTest.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityConfig securityConfig;

    @Test
    @WithMockUser
    void authenticatedUserCanAccessDemoEndpoint() throws Exception {
        mockMvc.perform(get("/demo/run"))
                .andExpect(status().isOk());
    }

    @Test
    void unauthenticatedUserCannotAccessDemoEndpoint() throws Exception {
        mockMvc.perform(get("/demo/run"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void authenticatedUserCannotAccessInvalidEndpoint() throws Exception {
        mockMvc.perform(get("/demo/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void authenticatedUserCanAccessDemoMessageEndpoint() throws Exception {
        mockMvc.perform(get("/demo/message"))
                .andExpect(status().isOk());
    }

    @Test
    void unauthenticatedUserCannotAccessDemoMessageEndpoint() throws Exception {
        mockMvc.perform(get("/demo/message"))
                .andExpect(status().isUnauthorized());
    }
}