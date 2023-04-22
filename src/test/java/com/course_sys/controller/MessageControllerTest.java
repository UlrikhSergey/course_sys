package com.course_sys.controller;

import com.course_sys.config.JwtAuthenticationFilter;
import com.course_sys.entity.Course;
import com.course_sys.entity.Message;
import com.course_sys.repository.MessageRepository;
import com.course_sys.service.JwtService;
import com.course_sys.service.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MessageControllerTest.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class MessageControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    JwtService jwtService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    MessageServiceImpl messageService;
    @MockBean
    MessageRepository messageRepository;

    Message message1 =
            Message.builder()
                    .id(1)
                    .emailFrom("ulrikh@gmail.com")
                    .emailTo("john@gmail.com")
                    .text("hello")
                    .build();
    Message message2 =
            Message.builder()
                    .id(1)
                    .emailFrom("antony@gmail.com")
                    .emailTo("john@gmail.com")
                    .text("John, it`s Antony")
                    .build();
    Message message3 =
            Message.builder()
                    .id(1)
                    .emailFrom("elena@gmail.com")
                    .emailTo("john@gmail.com")
                    .text("John, it`s Elena")
                    .build();
 /*   @Test
    void getAllMyMessages() throws Exception {
        List<Message> messages = new ArrayList<>(Arrays.asList(message1, message2, message3));

        Mockito.when(messageService.getAllMyMessagesForTest("john@gmail.com")).thenReturn(messages);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }*/

    @Test
    void writeMessage() {
    }

    @Test
    void getMessage() {
    }

    @Test
    void readMessage() {
    }
}