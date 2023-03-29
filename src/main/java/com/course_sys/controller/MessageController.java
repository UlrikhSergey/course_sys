package com.course_sys.controller;


import com.course_sys.entity.Message;
import com.course_sys.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/writemessage")
    public Message writeMessage(@RequestBody Message message) {
        messageService.writeMessage(message);
        return message;
    }


    @GetMapping("/getmessages")
    public List<Message> getAllMyMessages() {
        return messageService.getAllMyMessages();
    }

    @GetMapping("/getmessages/{id}")
    public Message getMessage(@PathVariable Integer id) {
        return messageService.getMessage(id);
    }

    @PutMapping("/getmessages/{id}")
    public Message readMessage(@PathVariable Integer id) {
        return messageService.readMessage(id);
    }

}
