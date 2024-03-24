package com.martynov.spring.api;

import com.martynov.spring.service.ChatGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat_group")
@RequiredArgsConstructor
public class ChatGroupApiController {

    private final ChatGroupService chatGroupService;
    @PostMapping
    public Map<String,String> create(@RequestParam("person_id") int personId) {
        chatGroupService.createChatGroup(personId);
        return Map.of("success","success");
    }
}
