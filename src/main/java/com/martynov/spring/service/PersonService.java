package com.martynov.spring.service;

import com.martynov.spring.models.Ability;
import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.PersonRepository;
import com.martynov.spring.security.PersonDetails;
import com.martynov.spring.util.PersonNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    @Value("${image_dir}")
    private String IMAGE_DIR;

    private final PersonRepository personRepository;
    private final EntityManager entityManager;

    public Person getCurrentPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    @Transactional
    public void uploadPhoto(MultipartFile imageFile) {
        Person person = getCurrentPerson();
        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = IMAGE_DIR;
                String originalFilename = imageFile.getOriginalFilename();
                String fileName = UUID.randomUUID() + "-" + (originalFilename != null ? originalFilename : "file");
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                person.setPhoto(path.toString());
            } catch (IOException e) {
                person.setPhoto(null);
            }
        }
        personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Resource> getCurrentPersonImage() {
        Person person = getCurrentPerson();
        return getResourceResponseEntity(person);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Resource> getPersonImageById(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return getResourceResponseEntity(person);
    }

    private ResponseEntity<Resource> getResourceResponseEntity(Person person) {
        if (person != null && person.getPhoto() != null) {
            try {
                Path filePath = Paths.get(person.getPhoto());
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity
                            .ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(resource);
                } else {
                    return ResponseEntity
                            .badRequest()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(null);
                }
            } catch (MalformedURLException e) {
                log.error(e.toString());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional(readOnly = true)
    public Set<Person> getPersonListWithRecommendation(int page, int count) {
        Person currentPerson = getCurrentPerson();
        Session session = entityManager.unwrap(Session.class);
        List<Ability> abilities = session.createQuery(
                        "select a from Ability a left join fetch a.skill where a.person.id=:personId",
                        Ability.class)
                .setParameter("personId", currentPerson.getId())
                .getResultList();
        List<Person> personList = session.createQuery(
                "select p from Person p where size(p.abilities) > 0 and p.id != :personId",
                        Person.class)
                .setParameter("personId", currentPerson.getId())
                .setFirstResult(page * count)
                .setMaxResults(count)
                .getResultList();
        for (Ability ability : abilities) {
            for (Person person : personList) {
                for (Ability ability1 : person.getAbilities()) {
                    if (((ability.getIsItMy() && !ability1.getIsItMy()) || (!ability.getIsItMy() && ability1.getIsItMy())) &&
                            ability.getSkill().getName().equalsIgnoreCase(ability1.getSkill().getName())) {
                        int sameness = person.getSameness() + 1;
                        person.setSameness(sameness);
                    }
                }
            }
        }
        Set<Person> personSet = new TreeSet<>((o1, o2) -> o2.getSameness() - o1.getSameness());
        personSet.addAll(personList);
        return personSet;
    }
}
