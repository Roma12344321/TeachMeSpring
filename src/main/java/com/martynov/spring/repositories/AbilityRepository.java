package com.martynov.spring.repositories;

import com.martynov.spring.models.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityRepository extends JpaRepository<Ability,Integer> {
}
