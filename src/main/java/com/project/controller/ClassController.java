package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.exception.ControllerException;
import com.project.exception.ServiceException;
import com.project.model.Classes;
import com.project.model.Student;
import com.project.service.ClassService;
import com.project.exception.ValidationException;

@RestController
@RequestMapping("/class")
public class ClassController {
	Logger logger = LoggerFactory.getLogger(ClassController.class);
	@Autowired
	private ClassService classService;
	@Autowired
	private ValidationException validationException;

	@PostMapping("/addClass")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> saveClass(@RequestBody  Classes classes) {
	    try {
	        logger.info("Adding class: {}", classes.getClassName());
	        Classes savedClass = classService.saveClass(classes);
	        return ResponseEntity.ok(savedClass);
	    } catch (ServiceException e) {
	      
	        ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
	        return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        
	        ControllerException ce = new ControllerException("609", "An unexpected error occurred");
	        return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@PutMapping("/updateClass")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> updateClass(@RequestBody Classes classes) {
	    try {
	        logger.info("Updating class: {}", classes.getClassId());
	        Classes updatedClass = classService.updateClass(classes);
	        if (updatedClass != null) {
	            return ResponseEntity.ok("Class updated successfully: " + classes.getClassId());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (ServiceException e) {
	        //log.error("ServiceException occurred: {}", e.getMessage());
	        ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
	        return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        //log.error("An unexpected error occurred: {}", e.getMessage());
	        ControllerException ce = new ControllerException("609", "An unexpected error occurred");
	        return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@DeleteMapping("/deleteClass/{classId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteClass(@PathVariable int classId) {
	    try {
	        logger.info("Deleting class: {}", classId);
	        classService.deleteClass(classId);
	        return ResponseEntity.ok("Class is deleted successfully");
	    } catch (ServiceException e) {
	        //log.error("ServiceException occurred: {}", e.getMessage());
	        ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
	        return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        //log.error("An unexpected error occurred: {}", e.getMessage());
	        ControllerException ce = new ControllerException("609", "An unexpected error occurred");
	        return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


//	@GetMapping("/getClassById/{classId}")
//    public Classes getStudentById(@PathVariable("classId") int classId) {
//        return classService.getClassById(classId);
//    }

}
