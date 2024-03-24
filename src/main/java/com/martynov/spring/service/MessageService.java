package com.martynov.spring.service;

import com.martynov.spring.models.ChatGroup;
import com.martynov.spring.models.Message;
import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final ChatGroupService chatGroupService;
    private final PersonService personService;
    @Transactional
    public void processAndSend(Message message, int chatGroupId) {
        ChatGroup chatGroup = chatGroupService.findById(chatGroupId);
        Person person = personService.getCurrentPerson();
        message.setChatGroup(chatGroup);
        message.setPerson(person);
        person.getMessages().add(message);
        chatGroup.getMessages().add(message);
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/publicChatRoom." + chatGroupId, message);
    }
}
