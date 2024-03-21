package com.martynov.spring.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "ability")
@Entity
@NoArgsConstructor
@Data
public class Ability {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "skill_id",referencedColumnName = "id")
    private Skill skill;

    @Column(name = "is_it_my")
    private Boolean isItMy;

    public Ability(Person person, Skill skill, Boolean isItMy) {
        this.person = person;
        this.skill = skill;
        this.isItMy = isItMy;
    }
}
