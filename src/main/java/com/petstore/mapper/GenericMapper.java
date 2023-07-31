package com.petstore.mapper;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.petstore.dto.BaseDto;
import com.petstore.entity.BaseEntity;

public interface GenericMapper<D extends BaseDto, E extends BaseEntity> {
    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toListEntities(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(toList());
    }

    default Set<E> toSetEntities(Set<D> dtoSet) {
        return dtoSet.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    default List<D> toListDtos(List<E> entitiesList) {
        return entitiesList.stream().map(this::toDto).collect(toList());
    }

    default Set<D> toSetDtos(Set<E> entitySet) {
        return entitySet.stream().map(this::toDto).collect(Collectors.toSet());
    }
}