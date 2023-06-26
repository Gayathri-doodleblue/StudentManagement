package com.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import com.project.exception.ControllerException;
import com.project.exception.ServiceException;
import com.project.exception.ValidationException;
import com.project.model.Student;
import com.project.model.Subject;
import com.project.service.SubjectService;
@RestController
@RequestMapping("/subject")
public class SubjectController {
	@Autowired
	private ValidationException validationException;
	Logger logger = LoggerFactory.getLogger(SubjectController.class);
	@Autowired
	private SubjectService subjectService;
	

	@PostMapping("/addSubject")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> saveSubject(@RequestBody Subject subject) {
	    try {
	    	logger.info("Adding subject: {}");
	        Subject savedSubject = subjectService.saveSubject(subject);
	        return ResponseEntity.ok(savedSubject);
	    } 
	    catch (ServiceException e) {
            //log.error("ServiceException occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            //log.error("An unexpected error occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException("609", "An unexpected error occurred");
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@GetMapping("/getSubjects")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getSubjects() {
	    try {
	    	
	    	logger.info("Get all subjects");
	        List<Subject> subjects = subjectService.getSubjects();
	        return ResponseEntity.ok(subjects);
	    } 
	    catch (ServiceException e) {
            //log.error("ServiceException occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            //log.error("An unexpected error occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException("609", "An unexpected error occurred");
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@PutMapping("/updateSubject")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> updateSubject(@RequestBody Subject subject) {
	    try {
	    	 logger.info("Updating subject: {}", subject.getSubjectId());
	        Subject updatedSubject = subjectService.updateSubject(subject);
	        if (updatedSubject != null) {
	            return ResponseEntity.ok("Subject edited successfully: " + subject.getSubjectId());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (ServiceException e) {
            //log.error("ServiceException occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            //log.error("An unexpected error occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException("609", "An unexpected error occurred");
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@DeleteMapping("/deleteSubject/{subjectId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> deleteSubject(@PathVariable int subjectId) {
	    try {
	    	 logger.info("Deleting subject: {}", subjectId);
	        subjectService.deleteSubject(subjectId);
	        return ResponseEntity.ok("Subject is deleted successfully");
	    } 
	    catch (ServiceException e) {
            //log.error("ServiceException occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            //log.error("An unexpected error occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException("609", "An unexpected error occurred");
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}


}

