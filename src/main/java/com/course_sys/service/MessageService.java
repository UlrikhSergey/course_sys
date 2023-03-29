package com.course_sys.service;

import com.course_sys.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> getAllMyMessages();

    Message getMessage(Integer id);

    Message readMessage(Integer id);

    Message writeMessage(Message message);
}
