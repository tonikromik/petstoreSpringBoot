package com.petstore.mapper;

import com.petstore.dto.OrdersDTO;
import com.petstore.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper extends GenericMapper<OrdersDTO, Orders>{
}