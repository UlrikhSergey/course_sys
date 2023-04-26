package com.course_sys.controller;

import com.course_sys.config.JwtAuthenticationFilter;
import com.course_sys.entity.Course;
import com.course_sys.service.CourseServiceImpl;
import com.course_sys.service.JwtService;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CourseRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class CourseRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    JwtService jwtService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    CourseServiceImpl courseService;


    Course course_1 =
            Course.builder()
                    .id(1)
                    .area("IT")
                    .name("Backend")
                    .cost(150).build();
    Course course_2 =
            Course.builder()
                    .id(2)
                    .area("IT")
                    .name("Frontend")
                    .cost(100).build();
    Course course_3 =
            Course.builder()
                    .id(3)
                    .area("HR")
                    .name("Hire skils")
                    .cost(200).build();

    @Test
    void getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>(Arrays.asList(course_1, course_2, course_3));

        Mockito.when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name").value("Backend"));
    }

    @Test
    public void getCoursetById() throws Exception {
        Mockito.when(courseService.getCourse(course_1.getId())).thenReturn(course_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("Backend"));
    }

    @Test
    public void saveCourse() throws Exception {
        Mockito.when(courseService.saveCourse(course_1)).thenReturn(course_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(course_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("Backend"));
    }


    @Test
    public void deleteCourseById() throws Exception {
        Mockito.when(courseService.getCourse(course_2.getId())).thenReturn(course_2);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/courses/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCoursesByArea() throws Exception {
        List<Course> courses = new ArrayList<>(Arrays.asList(course_1, course_2));
        Mockito.when(courseService.findByArea("IT")).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses").param("area","IT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name").value("Frontend"));
    }

    @Test
    void getCoursesCostAfter() throws Exception {
        List<Course> courses = new ArrayList<>(Arrays.asList(course_1, course_2,course_3));
        Mockito.when(courseService.findByCostAfter(90)).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses").param("min","90")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("Backend"));
    }

    @Test
    void getCoursesCostBefore() throws Exception {
        List<Course> courses = new ArrayList<>(Arrays.asList(course_1, course_2));
        Mockito.when(courseService.findByCostBefore(190)).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses").param("max","190")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Backend"));
    }

    @Test
    void getCoursesCostBetween() throws Exception {
        List<Course> courses = new ArrayList<>(Collections.singletonList(course_1));
        Mockito.when(courseService.findByCostBetween(110,190)).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses")
                        .param("min","110")
                        .param("max","190")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Backend"));
    }

    @Test
    void getCoursesNameContains() throws Exception {
        List<Course> courses = new ArrayList<>(Arrays.asList(course_1,course_2));
        Mockito.when(courseService.findByNameContains("end")).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses")
                        .param("name","end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Backend"));
    }
    }
