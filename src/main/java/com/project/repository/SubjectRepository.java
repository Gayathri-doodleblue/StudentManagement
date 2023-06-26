package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{

	Subject findBySubjectName(String subjectName);

}
