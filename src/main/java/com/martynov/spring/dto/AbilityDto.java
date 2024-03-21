package com.martynov.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AbilityDto {
    private int id;
    private SkillDto skill;
    private Boolean isItMy;
}
