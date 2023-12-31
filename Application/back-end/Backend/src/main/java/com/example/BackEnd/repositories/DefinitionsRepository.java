package com.example.BackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.entities.Definitions;

public interface DefinitionsRepository extends JpaRepository<Definitions, Long> {

    Definitions findByKey(String key);
}