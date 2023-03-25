package com.course_sys.service;

import com.course_sys.entity.Message;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MessageService {

    List<Message> getAllMyMessages();
    Message getMessage(Integer id);
    Message readMessage(Integer id);
    Message writeMessage(Message message);
}
