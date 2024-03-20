package com.martynov.spring.api;

import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonApiController {
    private final PersonService personService;

    @PostMapping("/upload")
    public Map<String,String> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        personService.uploadPhoto(photo);
        return Map.of("success","success");
    }
    
    @GetMapping()
    public ResponseEntity<Resource> getCurrentPersonImage() {
        return personService.getCurrentPersonImage();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getPersonImageById(@PathVariable int id) {
        return personService.getPersonImageById(id);
    }
}
