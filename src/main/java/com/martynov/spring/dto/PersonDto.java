package com.martynov.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Data
public class PersonDto {

    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Некорректное имя")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    @Email(message = "Некорректный email")
    @Size(min = 2, max = 120, message = "Некорректный email")
    private String email;

    @Size(min = 2, max = 100, message = "Некорректный город")
    private String city;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

}
