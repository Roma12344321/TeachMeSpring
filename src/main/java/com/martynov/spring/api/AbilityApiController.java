package com.martynov.spring.api;

import com.martynov.spring.dto.AbilityDto;
import com.martynov.spring.mapper.AbilityMapper;
import com.martynov.spring.models.Skill;
import com.martynov.spring.service.AbilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ability")
public class AbilityApiController {

    private final AbilityService abilityService;
    private final AbilityMapper abilityMapper;
    @PostMapping
    public Map<String,String> addAbility(@RequestBody Skill skill, @RequestParam("is_it_my") Boolean isItMy) {
        abilityService.addAbility(skill,isItMy);
        return Map.of("success","success");
    }
    @DeleteMapping
    public Map<String,String> deleteAbility(@RequestParam("skillId") Integer skillId) {
        abilityService.deleteAbility(skillId);
        return Map.of("success","success");
    }
    @GetMapping
    public List<AbilityDto> getAllAbilities() {
        return abilityMapper.mapAbilityDtoListToAbility(abilityService.findAllAbilitiesForCurrentPerson());
    }
}
