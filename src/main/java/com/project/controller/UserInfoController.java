package com.project.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.ControllerException;
import com.project.exception.ServiceException;
import com.project.exception.ValidationException;
import com.project.model.Student;
import com.project.model.UserInfo;
import com.project.service.UserInfoService;



@RestController
public class UserInfoController {
	@Autowired
	private ValidationException validationException;
	Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	@Autowired
	UserInfoService userInfoService;
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo) {
		try {
			
			logger.info("Adding User: {}");
			UserInfo saveduserInfo= userInfoService.addLoginDetails(userInfo);
			return ResponseEntity.ok(saveduserInfo);
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
	
//	@GetMapping("/signup")
//	 @PreAuthorize("hasAuthority('ROLE_USER')")
//	 public Optional<UserInfo> logInUser() {
//	  SecurityContext securityContext = SecurityContextHolder.getContext();
//	  Authentication authentication = securityContext.getAuthentication();
//	  String username = authentication.getName();
//	  return userInfoService.getUser(username);
//	 }
//	
	
}
