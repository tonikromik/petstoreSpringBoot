package com.petstore.petstoreRest.service.impl;

import com.petstore.petstoreRest.dto.UserDTO;
import com.petstore.petstoreRest.entity.User;
import com.petstore.petstoreRest.mapper.UserMapper;
import com.petstore.petstoreRest.repository.UserRepository;
import com.petstore.petstoreRest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDTO findByUsername(String username) {
        return userMapper.toDTO(userRepository.findByUserName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        //TODO: change to other exception
        //                .orElseThrow(() -> new EntityNotFoundException("User with username \"" + username + "\" not found")));
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<User> existedUser = userRepository.findByUserName(userDTO.getUserName());
        if (existedUser.isEmpty()) {
            User savedUser = userRepository.save(userMapper.toEntity(userDTO));
            return userMapper.toDTO(savedUser);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with such username already exists");
            //TODO: change exception
        }
    }


    @Override
    public void updateUser(String username, UserDTO userDTO) {
        User currentUser = userRepository.findByUserName(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //TODO: change exception

        currentUser.setUserName(userDTO.getUserName());
        currentUser.setFirstName(userDTO.getFirstName());
        currentUser.setLastName(userDTO.getLastName());
        currentUser.setEmail(userDTO.getEmail());
        currentUser.setPassword(userDTO.getPassword());
        currentUser.setPhone(userDTO.getPhone());
        currentUser.setUserStatus(userDTO.getUserStatus());

        userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUserName(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        //TODO: change exception
    }

    @Override
    public void saveAllUsers(List<UserDTO> usersDTO) {
        for (UserDTO userDTO : usersDTO) {
            if (userRepository.findByUserName(userDTO.getUserName()).isEmpty()) {
                userRepository.save(userMapper.toEntity(userDTO));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with such username already exist");
                //TODO: change exception
            }
        }
    }
}
