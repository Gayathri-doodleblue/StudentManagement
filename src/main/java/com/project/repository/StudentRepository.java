package com.project.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.project.model.Classes;
import com.project.model.Student;

import jakarta.transaction.Transactional;


public interface StudentRepository extends JpaRepository<Student, Integer>{
	 List<Student> findByClassesClassId(int classes);
	 
//	 
//	 @Modifying
//	 @Query(value ="SELECT * FROM Student b WHERE b.student_id IN :studentIds",nativeQuery = true)
//	 List<Student> findByStudentIds(@Param("studentIds") int studentId);
	 
	 
//	@Procedure(value="getStudents")
//	 public  Student findByStudentIds(@Param("studentIds") String studentIds,@Param("studentDetails") String[] studentDetails);
//	 @Procedure(value="getStudents",outputParameterName = "studentDetails")
//	 public  Student findByStudentIds(@Param("studentIds") int studentIds);

	 @Procedure(value = "fetchStudent" )
	   public Student findByStudentById(@Param("sId") int studentIds);
	 
	 @Procedure(value="getStudentName",outputParameterName = "studentname")
	 public String findByStudentName(int studentId);
	 
	 
	 @Procedure(name = "getByStudents")
	 public String findByStudentIds(@Param("studentIds") String studentIds);

	List<Student> findByClassesClassId(Classes classes);
	 


}
