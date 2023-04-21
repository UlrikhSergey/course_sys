package com.course_sys.service;

import com.course_sys.entity.Message;
import com.course_sys.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageServiceImplTest {
    @Mock
    private MessageRepository messageRepository;

    private Authentication authentication;

    private Message message1;

    private Message message2;

    @BeforeEach
    public void setUp() {
        this.authentication = new UsernamePasswordAuthenticationToken("genry@gmail.com", 12345);
        this.message1 = Message.builder()
                .id(1)
                .emailFrom("sergey@gmail.com")
                .emailTo("genry@gmail.com")
                .text("Hello")
                .isRead(false)
                .build();
        this.message2 = Message.builder()
                .id(2)
                .emailFrom("nastya@gmail.com")
                .emailTo("genry@gmail.com")
                .text("How are you?")
                .isRead(false)
                .build();
    }

    @Test
    void getAllMyMessages() {
        given(messageRepository.findByEmailTo("genry@gmail.com")).willReturn(List.of(message1, message2));
        String email = authentication.getName();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        assertThat(allMyMessages).isNotNull();
        assertThat(allMyMessages.size()).isEqualTo(2);
    }

    @Test
    void getMessage() {
        given(messageRepository.findById(1)).willReturn(Optional.of(message1));
        given(messageRepository.findByEmailTo("genry@gmail.com")).willReturn(List.of(message1, message2));
        List<Integer> listUserMessagesId = new ArrayList<>();
        String email = authentication.getName();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        for (Message message : allMyMessages) {
            listUserMessagesId.add(message.getId());
        }
        assertThat(allMyMessages).isNotNull();
        assertThat(allMyMessages.contains(message1));
        assertEquals(2, allMyMessages.size());

    }

    @Test
    void getMessageWhenIdMessageNotFound() {
        given(messageRepository.findById(2)).willReturn(Optional.empty());
        given(messageRepository.findByEmailTo("genry@gmail.com")).willReturn(List.of(message2));
        List<Integer> listUserMessagesId = new ArrayList<>();
        String email = authentication.getName();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        for (Message message : allMyMessages) {
            listUserMessagesId.add(message.getId());
        }
        assertThat(!listUserMessagesId.contains(2));
        assertThat(listUserMessagesId.size()).isEqualTo(1);

    }

    @Test
    void readMessage() {
        given(messageRepository.findByEmailTo("genry@gmail.com")).willReturn(List.of(message1, message2));
        given(messageRepository.findById(1)).willReturn(Optional.of(message1));
        given(messageRepository.save(message1)).willReturn(message1);
        message1.setRead(true);
        Message message = messageRepository.save(message1);
        assertThat(message.isRead()).isEqualTo(true);
    }

    @Test
    void writeMessage() {
        given(messageRepository.findById(message1.getId()))
                .willReturn(Optional.empty());
        given(messageRepository.save(message1)).willReturn(message1);
        message1.setEmailFrom(authentication.getName());
        Message message = messageRepository.save(message1);
        assertThat(message).isNotNull();
    }


}