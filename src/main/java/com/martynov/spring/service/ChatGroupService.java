package com.martynov.spring.service;

import com.martynov.spring.models.ChatGroup;
import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.ChatGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGroupService {

    private final PersonService personService;
    private final ChatGroupRepository chatGroupRepository;

    @Transactional
    public void createChatGroup(int personId) {
        Person currentPerson = personService.getCurrentPerson();
        Person person = personService.findById(personId);
        String name = "chat of " + currentPerson.getUsername() + " and " + person.getUsername();
        ChatGroup chatGroup = new ChatGroup(name);
        chatGroup.setPeople(List.of(currentPerson,person));
        chatGroupRepository.save(chatGroup);
    }
    @Transactional(readOnly = true)
    public ChatGroup findById(int id) {
        return chatGroupRepository.findById(id).orElseThrow();
    }
}
