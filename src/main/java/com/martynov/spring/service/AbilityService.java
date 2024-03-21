package com.martynov.spring.service;

import com.martynov.spring.models.Ability;
import com.martynov.spring.models.Person;
import com.martynov.spring.models.Skill;
import com.martynov.spring.repositories.AbilityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbilityService {

    private final PersonService personService;
    private final SkillService skillService;
    private final AbilityRepository abilityRepository;
    private final EntityManager entityManager;

    @Transactional
    public void addAbility(Skill skill, Boolean isItMy) {
        skillService.addSkill(skill);
        Person person = personService.getCurrentPerson();
        Ability ability = new Ability(person, skill, isItMy);
        abilityRepository.save(ability);
    }

    @Transactional
    public void deleteAbility(int skillId) {
        Person person = personService.getCurrentPerson();
        Session session = entityManager.unwrap(Session.class);
        Ability ability = session.createQuery(
                        "select a from Ability a where a.person.id=:personId and a.skill.id=:skillId",
                        Ability.class)
                .setParameter("personId", person.getId())
                .setParameter("skillId", skillId)
                .getSingleResultOrNull();
        if (ability == null) {
            throw new RuntimeException();
        }
        abilityRepository.delete(ability);
        Skill skill = skillService.findById(skillId).orElseThrow(RuntimeException::new);
        if (skill.getAbilities().isEmpty()) {
            skillService.deleteSkill(skill);
        }
    }

    @Transactional(readOnly = true)
    public List<Ability> findAllAbilitiesForCurrentPerson() {
        Person person = personService.getCurrentPerson();
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(
                "select a from Ability a left join fetch a.skill where a.person.id=:personId",
                        Ability.class)
                .setParameter("personId",person.getId())
                .getResultList();
    }
}
