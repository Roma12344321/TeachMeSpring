package com.martynov.spring.mapper;

import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.models.Person;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class PersonMapper {

    private final ModelMapper modelMapper;
    public Person mapPersonDtoToPerson(PersonDto personDto) {
        return modelMapper.map(personDto,Person.class);
    }
    public PersonDto mapPersonToPersonDto(Person person) {
        return modelMapper.map(person,PersonDto.class);
    }
    public List<PersonDto> mapSetPersonToListPersonDto(Set<Person> personSet) {
        return personSet.stream().map(this::mapPersonToPersonDto).toList();
    }
}
