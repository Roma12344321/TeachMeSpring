package com.martynov.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Некорректное имя")
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "city")
    @Size(min = 2, max = 100, message = "Некорректный город")
    private String city;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "person")
    private List<Ability> abilities;

    @Transient
    private int sameness;

    @ManyToMany(mappedBy = "people")
    private List<ChatGroup> chatGroups;

    @OneToMany(mappedBy = "person")
    private List<Message> messages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(username, person.username) && Objects.equals(password, person.password) && Objects.equals(email, person.email) && Objects.equals(photo, person.photo) && Objects.equals(city, person.city) && Objects.equals(date, person.date) && Objects.equals(createdAt, person.createdAt) && Objects.equals(role, person.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, photo, city, date, createdAt, role);
    }
}
