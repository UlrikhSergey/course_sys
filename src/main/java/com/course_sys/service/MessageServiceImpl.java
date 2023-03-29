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

    //Метод для получения всех сообщений пользователя
    @Override
    public List<Message> getAllMyMessages() {
        String email = getEmailFromAuth();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        return allMyMessages;
    }

    //Метод для получения сообщения по id
    @Override
    public Message getMessage(Integer id) {
        List<Integer> listUsersMessagesId = listOfMyMessagesId();

        Message message = null;
        if (!listUsersMessagesId.contains(id)) {
            System.out.println("message not found");
        } else {
            message = messageRepository.findById(id).get();
        }
        return message;
    }

    //Метод для изменения статуса сообщения на "прочитано"
    @Override
    public Message readMessage(Integer id) {
        List<Integer> listUsersMessagesId = listOfMyMessagesId();
        Message message = null;
        if (!listUsersMessagesId.contains(id)) {
            System.out.println("message not found");
        } else {
            message = messageRepository.findById(id).get();
            message.setRead(true);
            messageRepository.save(message);
        }
        return message;
    }

    //Метод для создания сообщения
    @Override
    public Message writeMessage(Message message) {
        String email = getEmailFromAuth();
        message.setEmailFrom(email);
        messageRepository.save(message);
        return message;
    }

    //вспомогательный метод для получения email аутентифицированного пользователя
    private String getEmailFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    //получение списка id сообщений, которые адресованы текущему аутентифицированному пользователю
    private List<Integer> listOfMyMessagesId(){
        String email = getEmailFromAuth();
        List<Message> allMyMessages = messageRepository.findByEmailTo(email);
        List<Integer> listUsersMessagesId = new ArrayList<>();
        for (Message message : allMyMessages) {
            listUsersMessagesId.add(message.getId());
        }
        return listUsersMessagesId;
    }
}
