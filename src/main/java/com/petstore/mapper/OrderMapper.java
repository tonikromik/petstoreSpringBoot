package com.petstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.petstore.dto.OrderDto;
import com.petstore.entity.Order;

@Mapper(componentModel = "spring", uses = PetMapper.class)
public interface OrderMapper extends GenericMapper<OrderDto, Order> {
    @Mapping(target = "shipDate", expression = "java(java.time.LocalDateTime.now())")
    @Override
    Order toEntity(OrderDto dto);
}