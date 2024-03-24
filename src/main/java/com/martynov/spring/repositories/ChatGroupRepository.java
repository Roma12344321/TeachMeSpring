package com.martynov.spring.repositories;

import com.martynov.spring.models.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGroupRepository extends JpaRepository<ChatGroup,Integer> {
}
