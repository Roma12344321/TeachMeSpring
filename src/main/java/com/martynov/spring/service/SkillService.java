package com.martynov.spring.service;

import com.martynov.spring.models.Skill;
import com.martynov.spring.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    @Transactional
    public void addSkill(Skill skill) {
        skillRepository.save(skill);
    }
    @Transactional
    public void deleteSkill(Skill skill) {
        skillRepository.delete(skill);
    }
    @Transactional(readOnly = true)
    public List<Skill> findAllSkill() {
        return skillRepository.findAll();
    }
    public Optional<Skill> findById(int id) {
        return skillRepository.findById(id);
    }
}
