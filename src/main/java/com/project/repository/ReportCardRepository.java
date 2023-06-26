package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.project.model.ReportCard;
import com.project.model.Student;

public interface ReportCardRepository extends JpaRepository<ReportCard, Integer> {

	@Modifying
	@Query("DELETE FROM ReportCard r WHERE r.student.studentId = :studentId")
	void deleteByStudentId(@Param("studentId") int studentId);
//	@Modifying
//	@Procedure(value = "deleteStudent")
	//public ReportCard deleteByStudentId(@Param("studentId") int studentIds);

	List<ReportCard> findByStudent(Student student);

//	public void deleteByStudentId(int studentId);


}
