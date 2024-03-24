package com.martynov.spring.controller;

import com.martynov.spring.models.Message;
import com.martynov.spring.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat.sendMessage/{chatGroupId}")
    public void sendMessage(@Payload Message chatMessage, @DestinationVariable int chatGroupId) {
        messageService.processAndSend(chatMessage, chatGroupId);
    }

}
