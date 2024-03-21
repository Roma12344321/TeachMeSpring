package com.martynov.spring.mapper;

import com.martynov.spring.dto.AbilityDto;
import com.martynov.spring.models.Ability;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AbilityMapper {

    private final ModelMapper mapper;

    private AbilityDto mapAbilityToAbilityDto(Ability ability) {
        return mapper.map(ability, AbilityDto.class);
    }
    public List<AbilityDto> mapAbilityDtoListToAbility(List<Ability> abilities) {
        return abilities.stream().map(this::mapAbilityToAbilityDto).toList();
    }
}
