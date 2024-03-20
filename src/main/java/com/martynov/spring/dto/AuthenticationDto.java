package com.martynov.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationDto {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2,max = 100,message = "Некорректное имя")
    private String username;
    private String password;
}
