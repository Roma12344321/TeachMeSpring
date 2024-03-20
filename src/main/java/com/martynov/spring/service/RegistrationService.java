package com.martynov.spring.service;

import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.PersonRepository;
import com.martynov.spring.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setRole(Role.ROLE_USER);
        person.setCreatedAt(new Date());
        person.setPassword(encodedPassword);
        personRepository.save(person);
    }
}
