package com.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.controller.StudentController;
import com.project.exception.ServiceException;
import com.project.model.Classes;
import com.project.model.Student;
import com.project.model.Subject;
import com.project.repository.ClassRepository;
import com.project.repository.ReportCardRepository;
import com.project.repository.StudentRepository;
import com.project.repository.SubjectRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Service

public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ClassRepository classesRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ReportCardRepository reportCardRepository;

	@Autowired
	EntityManager em;
	Logger logger = LoggerFactory.getLogger(StudentService.class);

	public Student addStudent(Student student, int classId) {
		try {
			Classes classes = classesRepository.findById(classId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid class ID: " + classId));

			student.setClasses(classes);

			return studentRepository.save(student);
		} catch (Exception e) {
			throw new ServiceException("605", "Failed to add student. Please provide valid details.");
		}
	}

	@CachePut(cacheNames = "students", key = "#student.studentId")
	public Student updateStudent(Student student) {
		try {
			Student existingStudent = studentRepository.findById(student.getStudentId()).orElseThrow(
					() -> new IllegalArgumentException("Student not found with ID: " + student.getStudentId()));

			existingStudent.setFirstName(student.getFirstName());
			existingStudent.setLastName(student.getLastName());
			existingStudent.setAddress(student.getAddress());
			existingStudent.setPhoneNumber(student.getPhoneNumber());
			existingStudent.setClasses(student.getClasses());

			return studentRepository.save(existingStudent);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("606", e.getMessage());
		} catch (Exception e) {
			throw new ServiceException("606", "Enter proper studentID to update the existing student");
		}
	}

//
	@Cacheable(cacheNames = "students", key = "#studentId")
	public Student getStudentById(int studentId) {
		try {
			System.out.println("caching");
			return studentRepository.findById(studentId)
					.orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));

		} catch (NoSuchElementException e) {
			throw new ServiceException("606", e.getMessage());
		} catch (Exception e) {
			throw new ServiceException("606", "Enter proper studentId to get the existing studentCard");
		}
	}

	@Cacheable(cacheNames = "student", key = "#classId")
	public List<Student> getStudentsByClassId(int classId) {
		try {
			return studentRepository.findByClassesClassId(classId);
		} catch (NoSuchElementException e) {
			throw new ServiceException("606", e.getMessage());
		} catch (Exception e) {
			throw new ServiceException("606", "Enter proper studentId to get the existing student");
		}
	}

	@Transactional
	public Student fetchByStudentId(int sId) {
		return studentRepository.findByStudentById(sId);
	}

	@CacheEvict(cacheNames = "student", key = "studentId")
	@Transactional
	public void deleteStudent(int studentId) {
		// try {
		// Retrieve the student
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));
		if (student == null) {
			// Handle the case when the student does not exist
			throw new IllegalArgumentException("Student not found");
		}

		// Delete report cards associated with the student
		reportCardRepository.deleteByStudentId(studentId);

		// Remove the association between the student and the class
		student.setClasses(null); // Assuming there is a class association in the Student entity

		// Save the updated student entity
		studentRepository.save(student);

		// Delete the student
		studentRepository.deleteById(studentId);
	}
//	    }catch (NoSuchElementException e) {
//            throw new ServiceException("606", e.getMessage());
//        } catch (Exception e) {
//            throw new ServiceException("606", "Enter proper studentIds to get the existing students");
//        }
//	    }

//	    public String fetchByStudentName(@PathVariable int studentId)
//		{
//			return studentRepository.findByStudentName(studentId);
//		}

	@Transactional
	public String getStudents(List<Integer> studentIds) {
		String studentIdsAsString = StringUtils.join(studentIds, ",");
		return studentRepository.findByStudentIds(studentIdsAsString);
	}

//	    @Transactional
//	    public List<Map<String, Object>> getStudents(List<Integer> studentIds) {
//	        String studentIdsString = StringUtils.join(studentIds, ",");
//	        Map<String, Object> studentDetails = new HashMap<>();
//	        studentRepository.getStudents(studentIdsString, studentDetails);
//	        List<Map<String, Object>> studentDetailsList = new ArrayList<>();
//	        studentDetailsList.add(studentDetails);
//	        return studentDetailsList;
//	    }

//	    @Cacheable(cacheNames = "cacheStore", key = "#name")
//	    public Student generatePerson(String firstName) {
//	    	Student student = new Student(UUID.randomUUID().toString(), name, "Switzerland");
//	        logger.info("Generated Person: {}", person);
//	        return student;
//	    }
//}
}
