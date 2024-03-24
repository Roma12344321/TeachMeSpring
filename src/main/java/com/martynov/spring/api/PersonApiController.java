package com.martynov.spring.api;

import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.mapper.PersonMapper;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping
    public List<PersonDto> showAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "count", required = false, defaultValue = "20") int count) {
        return personMapper.mapListPersonToListPersonDto(personService.getAllPerson(page, count));
    }

    @GetMapping("/rec")
    public List<PersonDto> showAllWithRecommend(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "count", required = false, defaultValue = "100") int count) {
        return personMapper.mapSetPersonToListPersonDto(personService.getPersonListWithRecommendation(page, count));
    }

    @PostMapping("/image/upload")
    public Map<String, String> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        personService.uploadPhoto(photo);
        return Map.of("success", "success");
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
