package com.petstore.mapper;

import com.petstore.dto.PetDTO;
import com.petstore.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {TagMapper.class, CategoryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper extends GenericMapper<PetDTO, Pet> {
    @Mapping(target = "status", defaultValue = "AVAILABLE")
    @Override
    Pet toEntity(PetDTO petDTO);

    @Mapping(target = "id", ignore = true)
    void updateProperties(PetDTO petDTO, @MappingTarget Pet pet);

    @Override
    PetDTO toDTO(Pet entity);
}