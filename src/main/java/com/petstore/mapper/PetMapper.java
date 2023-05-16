package com.petstore.mapper;

import com.petstore.dto.PetDTO;
import com.petstore.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TagMapper.class, CategoryMapper.class})
public interface PetMapper extends GenericMapper<PetDTO, Pet> {
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "status", defaultValue = "AVAILABLE")
    @Override
    Pet toEntity(PetDTO petDTO);

    void updateProperties(PetDTO petDTO, @MappingTarget Pet pet);

    @Override
    PetDTO toDTO(Pet entity);
}
