package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.OrdersDTO;
import com.petstore.petstoreRest.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper extends GenericMapper<OrdersDTO, Orders>{
}