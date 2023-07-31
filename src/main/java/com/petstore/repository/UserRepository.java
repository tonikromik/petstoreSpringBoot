package com.petstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
}