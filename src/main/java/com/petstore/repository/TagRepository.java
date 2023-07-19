package com.petstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
