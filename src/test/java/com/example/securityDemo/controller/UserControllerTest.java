package com.example.securityDemo.controller;

import com.example.securityDemo.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InMemoryUserDetailsManager userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    }

    @Test
    void registerUserSuccessfully() throws Exception {
        mockMvc.perform(post("/user/register")
                .param("username", "testUser")
                .param("password", "testPassword")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        verify(userDetailsService, times(1)).createUser(any());
    }

@Test
void registerUserWithExistingUsername() throws Exception {
    doThrow(new UserAlreadyExistsException("User already exists")).when(userDetailsService).createUser(any());

    mockMvc.perform(post("/user/register")
            .param("username",  "existingUser")
            .param("password", "testPassword")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isConflict())
            .andExpect(content().string("User already exists"));

    verify(userDetailsService, times(1)).createUser(any());
}

    @Test
    void loginUserSuccessfully() throws Exception {
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }
}