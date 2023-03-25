package com.course_sys.service;

import com.course_sys.entity.Message;
import com.course_sys.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Override
    public List<Message> getAllMyMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        return allMyMessages;
    }

    @Override
    public Message getMessage(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        List<Integer> listUsersMessagesId = new ArrayList<>();
        for (Message message : allMyMessages) {
            listUsersMessagesId.add(message.getId());
        }
        Message message1 = null;
        if (!listUsersMessagesId.contains(id)) {
            System.out.println("message not found");
        } else {
            message1 = messageRepository.findById(id).get();
        }
        return message1;
    }

    @Override
    public Message readMessage(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        List<Integer> listUsersMessagesId = new ArrayList<>();
        for (Message message : allMyMessages) {
            listUsersMessagesId.add(message.getId());
        }
        Message message1 = null;
        if (!listUsersMessagesId.contains(id)) {
            System.out.println("message not found");
        } else {
            message1 = messageRepository.findById(id).get();
            message1.setRead(true);
            messageRepository.save(message1);
        }
        return message1;
    }

    @Override
    public Message writeMessage(Message message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        message.setEmailFrom(currentPrincipalName);
        messageRepository.save(message);
        return message;
    }
}
