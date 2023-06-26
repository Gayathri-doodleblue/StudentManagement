package com.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.project.config.UserInfoUserDetailsService;
import com.project.dto.AuthorizationUtils;
import com.project.exception.ControllerException;
import com.project.exception.ServiceException;
import com.project.exception.ValidationException;
import com.project.model.Classes;
import com.project.model.Student;

import com.project.model.UserInfo;
import com.project.repository.StudentRepository;
import com.project.repository.UserInfoRepository;
import com.project.service.ReportCardService;
import com.project.service.StudentService;
import com.project.service.UserInfoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/student")
@EnableMethodSecurity
public class StudentController {

	Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoUserDetailsService userInfoUserDetailsService;

	@Autowired
	private ReportCardService reportCardService;

	private final AuthorizationUtils authorizationUtils = new AuthorizationUtils();
	@Autowired
	private ValidationException validationException;

	@PostMapping("/addStudent")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> addStudentWithClass(@RequestBody Student student) {
		try {
			logger.info("Adding student: {}", student.getStudentId());
			Integer classId = student.getClasses().getClassId();
			Student newStudent = studentService.addStudent(student, classId);
			return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
		} catch (IllegalArgumentException e) {
			
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			
			ControllerException ce = new ControllerException("609", "An unexpected error occurred");
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateStudent")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> updateStudent(@RequestBody Student student) {
		try {
			logger.info("Updating student with ID: {}", student.getStudentId());
			Student updatedStudent = studentService.updateStudent(student);
			if (updatedStudent != null) {
				return ResponseEntity.ok("Student edited successfully: " + student.getStudentId());
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (ServiceException e) {
			// log.error("ServiceException occurred: {}", e.getMessage());
			ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// log.error("An unexpected error occurred: {}", e.getMessage());
			ControllerException ce = new ControllerException("609", "An unexpected error occurred");
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping("/deleteStudent/{studentId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteStudent(@PathVariable int studentId) {
		//try {
			logger.info("Deleting student with ID: {}", studentId);
			studentService.deleteStudent(studentId);
			return ResponseEntity.ok("Student deleted successfully");
//		} catch (ServiceException e) {
//			// log.error("ServiceException occurred: {}", e.getMessage());
//			ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
//			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch (Exception e) {
//			// log.error("An unexpected error occurred: {}", e.getMessage());
//			ControllerException ce = new ControllerException("609", "An unexpected error occurred");
//			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}


	@GetMapping("/byClass/{classId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getStudentsByClassId(@PathVariable int classId) {
		try {
			logger.info("Retrieving students by class ID: {}", classId);
			List<Student> students = studentService.getStudentsByClassId(classId);
			if (students.isEmpty()) {
				throw new ServiceException("606", "No students found with the given studentIds");
			}
			return ResponseEntity.ok(students);
		} catch (ServiceException e) {
			// log.error("ServiceException occurred: {}", e.getMessage());
			ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// log.error("An unexpected error occurred: {}", e.getMessage());
			ControllerException ce = new ControllerException("609", "An unexpected error occurred");
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@GetMapping("/getStudents")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	public ResponseEntity<?> getStudents(@RequestBody Map<String, List<Integer>> request) {
//		//try {
//			logger.info("Retrieving students");
//			List<Integer> studentIds = request.get("studentIds");
//			List<Student> students = studentService.getStudents(studentIds);
//
//			if (students.isEmpty()) {
//				throw new ServiceException("606", "No students found with the given studentIds");
//			}
//
//			return ResponseEntity.ok(students);
//		} catch (ServiceException e) {
//			ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
//			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch (Exception e) {
//			ControllerException ce = new ControllerException("609", "An unexpected error occurred");
//			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	//}
	
//	@GetMapping("/getStudents")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<?> getStudents(@RequestBody Map<String, List<Integer>> request) {
//        List<Integer> studentIds = request.get("studentIds");
//        List<Student> students = studentService.getStudents(studentIds);
//
//        if (students.isEmpty()) {
//            throw new ServiceException("606", "No students found with the given studentIds");
//        }
//
//        return ResponseEntity.ok(students);
//    }
	
//	@PostMapping("/getStudents")
//    public ResponseEntity<Map<String, Object>> getStudents(@RequestBody Map<String, String> requestBody) {
//        String studentIds = requestBody.get("studentIds");
//        Map<String, Object> studentDetails = studentService.executeGetStudentsProcedure(studentIds);
//        
//        if (studentDetails.isEmpty()) {
//            // Handle the case where no student details are found
//            return ResponseEntity.notFound().build();
//        }
//        
//        return ResponseEntity.ok(studentDetails);
//    }


	@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN')")
	@GetMapping("/getStudentById/{studentId}")
	//@Cacheable(cacheNames ="students",key = "#studentId")
	public ResponseEntity<?> getStudentById(@PathVariable int studentId, Authentication authentication) {
		try {
			logger.info("Retrieving students: {}", studentId);
			Student student = studentService.getStudentById(studentId);
			String username = authentication.getName();

			if (student != null) {
				Optional<UserInfo> userInfoOptional = userInfoService.getUserInfoByUsername(username);

				if (userInfoOptional.isPresent()) {
					UserInfo userInfo = userInfoOptional.get();

					if (authorizationUtils.isAuthorizedStudent(student, username, userInfo) || authentication
							.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
						return ResponseEntity.ok(student);
					} else {
						logger.warn("Unauthorized access: Username or FirstName does not match.");
					}
				} else {
					logger.warn("UserInfo not found for username: {}", username);
				}
			} else {
				logger.warn("Student not found with ID: {}", studentId);
			}

			return ResponseEntity.notFound().build();
		} catch (ServiceException e) {
			// log.error("ServiceException occurred: {}", e.getMessage());
			ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("An unexpected error occurred: {}", e.getMessage());
			ControllerException ce = new ControllerException("609", "An unexpected error occurred");
			return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	 @GetMapping("/fetchStudent")
	    public Student fetchByStudentId( @RequestBody Map<String, Object>request) {
		int sId = Integer.parseInt(request.get("sId").toString()) ;
		
	    	return studentService.fetchByStudentId(sId);
	    }
	@Transactional
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	 @GetMapping("/fetchStudent/{studentId}")
	public String fetchByStudentName(@PathVariable int studentId)
	{
		return studentRepository.findByStudentName(studentId);
	}
	
	@GetMapping("/getStudents")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> getStudents(@RequestBody Map<String, List<Integer>> request) {
	    List<Integer> studentIds = request.get("studentIds");
	    String studentDetails = studentService.getStudents(studentIds);

	    if (studentDetails == null || studentDetails.isEmpty()) {
	        throw new ServiceException("606", "No students found with the given studentIds");
	    }

	    return ResponseEntity.ok(studentDetails);
	}
	

	


}
