package com.petstore.mapper;

import com.petstore.dto.BaseDTO;
import com.petstore.entity.BaseEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public interface GenericMapper<D extends BaseDTO, E extends BaseEntity> {
    E toEntity(D dto);

    D toDTO(E entity);

    default List<E> toListEntities(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(toList());
    }
    default Set<E> toSetEntities(Set<D> dtoSet) {
        return dtoSet.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    default List<D> toListDTOs(List<E> entitiesList) {
        return entitiesList.stream().map(this::toDTO).collect(toList());
    }

//    @Mapping(target = "id", ignore = true)
//    void updateProperties(D dto, @MappingTarget E entity);
}