package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.PetDTO;
import com.petstore.petstoreRest.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class, CategoryMapper.class})
public interface PetMapper extends GenericMapper<PetDTO, Pet> {
    @Mapping(target = "tags", ignore = true)
    @Override
    Pet toEntity(PetDTO petDTO);
    @Override
    void updateProperties(PetDTO petDTO, @MappingTarget Pet pet);
}
