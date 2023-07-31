package com.petstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.petstore.dto.PetDto;
import com.petstore.entity.Pet;

@Mapper(componentModel = "spring", uses = {TagMapper.class, CategoryMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper extends GenericMapper<PetDto, Pet> {
    @Mapping(target = "status", defaultValue = "AVAILABLE")
    @Override
    Pet toEntity(PetDto petDto);

    @Mapping(target = "id", ignore = true)
    void updateProperties(PetDto petDto, @MappingTarget Pet pet);

    @Override
    PetDto toDto(Pet entity);
}