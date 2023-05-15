package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.BaseDTO;
import com.petstore.petstoreRest.entity.BaseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface GenericMapper<D extends BaseDTO, E extends BaseEntity> {
    E toEntity(D dto);

    D toDTO(E entity);

    default List<E> toListEntities(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(toList());
    }

    default List<D> toListDTOs(List<E> entitiesList) {
        return entitiesList.stream().map(this::toDTO).collect(toList());
    }

    @Mapping(target = "id", ignore = true)
    void updateProperties(D dto, @MappingTarget E entity);
}