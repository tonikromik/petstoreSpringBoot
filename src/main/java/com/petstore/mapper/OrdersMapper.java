package com.petstore.mapper;

import com.petstore.dto.OrderDTO;
import com.petstore.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PetMapper.class)
public interface OrdersMapper extends GenericMapper<OrderDTO, Order>{
    @Mapping(target = "shipDate", expression = "java(java.time.LocalDateTime.now())")
    @Override
    Order toEntity(OrderDTO dto);
}