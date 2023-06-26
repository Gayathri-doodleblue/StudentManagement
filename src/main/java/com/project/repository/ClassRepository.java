package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Classes;


public interface ClassRepository extends JpaRepository<Classes, Integer> {

	Classes findByClassName(String className);

}
