package com.martynov.spring.api;

import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.mapper.PersonMapper;
import com.martynov.spring.models.Person;
import com.martynov.spring.security.PersonDetails;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping
    public List<PersonDto> showAll() {
        return personMapper.mapSetPersonToListPersonDto(personService.getPersonListWithRecommendation(0,100));
    }

    @PostMapping("/image/upload")
    public Map<String,String> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        personService.uploadPhoto(photo);
        return Map.of("success","success");
    }
    
    @GetMapping("/image")
    public ResponseEntity<Resource> getCurrentPersonImage() {
        return personService.getCurrentPersonImage();
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getPersonImageById(@PathVariable int id) {
        return personService.getPersonImageById(id);
    }
}
