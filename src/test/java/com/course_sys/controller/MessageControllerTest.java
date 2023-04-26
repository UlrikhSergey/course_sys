package com.course_sys.controller;

import com.course_sys.config.JwtAuthenticationFilter;
import com.course_sys.entity.Message;
import com.course_sys.repository.MessageRepository;
import com.course_sys.service.JwtService;
import com.course_sys.service.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    JwtService jwtService;
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
                    .id(2)
                    .emailFrom("antony@gmail.com")
                    .emailTo("john@gmail.com")
                    .text("John, it`s Antony")
                    .build();
    Message message3 =
            Message.builder()
                    .id(3)
                    .emailFrom("elena@gmail.com")
                    .emailTo("john@gmail.com")
                    .text("John, it`s Elena")
                    .build();

    @WithMockUser(username = "john@gmail.com", password = "pwd", roles = "USER")
    @Test
    void getAllMyMessages() throws Exception {
        List<Message> messages = new ArrayList<>(Arrays.asList(message1, message2, message3));

        Mockito.when(messageService.getAllMyMessages()).thenReturn(messages);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authentication.getName(),message1.getEmailTo());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void writeMessage() throws Exception {
        Mockito.when(messageService.writeMessage(message1)).thenReturn(message1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(message1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void getMessage() throws Exception {
        Mockito.when(messageService.getMessage(message1.getId())).thenReturn(message1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void readMessage() throws Exception{
        Message updatedMesage = Message.builder()
                .id(1)
                .emailFrom("ulrikh@gmail.com")
                .emailTo("john@gmail.com")
                .text("hello")
                .isRead(true)
                .build();
            Mockito.when(messageService.getMessage(message1.getId())).thenReturn(message1);
            Mockito.when(messageService.readMessage(updatedMesage.getId())).thenReturn(updatedMesage);

            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/messages/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(this.mapper.writeValueAsString(updatedMesage));

            mockMvc.perform(mockRequest)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", notNullValue()));

    }
}