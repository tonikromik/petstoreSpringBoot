package com.petstore.mapper;

import com.petstore.dto.OrdersDTO;
import com.petstore.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PetMapper.class)
public interface OrdersMapper extends GenericMapper<OrdersDTO, Orders>{
    @Mapping(target = "shipDate", expression = "java(java.time.LocalDateTime.now())")
    @Override
    Orders toEntity(OrdersDTO dto);
}