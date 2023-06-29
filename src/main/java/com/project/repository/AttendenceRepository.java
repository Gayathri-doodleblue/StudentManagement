package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Attendance;

public interface AttendenceRepository extends JpaRepository<Attendance, Integer> {

}
