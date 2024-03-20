package com.martynov.spring.api;

import com.martynov.spring.dto.AuthenticationDto;
import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.mapper.PersonMapper;
import com.martynov.spring.models.Person;
import com.martynov.spring.security.JWTUtil;
import com.martynov.spring.service.PersonService;
import com.martynov.spring.service.RegistrationService;
import com.martynov.spring.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final PersonMapper personMapper;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDto personDto, BindingResult bindingResult) {
        Person person = personMapper.mapPersonDtoToPerson(personDto);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return Map.of("error", "error");
        registrationService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt_token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDto.getUsername(), authenticationDto.getPassword()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        return Map.of("jwt_token", token);
    }
}