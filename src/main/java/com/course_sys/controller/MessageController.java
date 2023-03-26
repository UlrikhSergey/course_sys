package com.course_sys.controller;


import com.course_sys.entity.Message;
import com.course_sys.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/writemessage")
    public Message writeMessage(@RequestBody Message message) {
        messageService.writeMessage(message);
        return message;
    }


    @GetMapping("/getmessages")
    public List<Message> getAllMyMessages() {
        List<Message> allMyMessages = messageService.getAllMyMessages();
        return allMyMessages;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getmessages/{id}")
    public Message getMessage(@PathVariable Integer id) {
        Message message = messageService.getMessage(id);
        return message;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/getmessages/{id}")
    public Message readMessage(@PathVariable Integer id){
        Message message = messageService.readMessage(id);
        return message;
    }

}
