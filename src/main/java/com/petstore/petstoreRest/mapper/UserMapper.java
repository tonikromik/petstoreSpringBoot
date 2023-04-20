package com.petstore.petstoreRest.mapper;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
