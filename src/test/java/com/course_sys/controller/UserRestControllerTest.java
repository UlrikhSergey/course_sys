package com.course_sys.controller;

import com.course_sys.config.JwtAuthenticationFilter;
import com.course_sys.entity.Course;
import com.course_sys.entity.Role;
import com.course_sys.entity.User;
import com.course_sys.service.CourseServiceImpl;
import com.course_sys.service.JwtService;
import com.course_sys.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

class UserRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    JwtService jwtService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    UserServiceImpl userService;

    User user_1 = User.builder()
            .id(1)
            .email("ulrikh@gmail.com")
            .firstname("sergey")
            .lastname("ulrikh")
            .role(Role.ADMIN)
            .password("12345")
            .rating(0)
            .build();
    User user_2 = User.builder()
            .id(2)
            .email("genry@gmail.com")
            .firstname("genry")
            .lastname("ivanov")
            .role(Role.ADMIN)
            .build();
    @Test
    void getAllUsers()  throws Exception{
        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2));

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void getUser() throws Exception{
        Mockito.when(userService.getUser(user_1.getId())).thenReturn(user_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

    }

}