package com.project.listeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.controller.StudentController;

//import com.project.event.AuthenticationSuccessEvent;

@Component
public class AuthenticationSuccessEventListener {
	Logger logger = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);
	 @Async
	@EventListener
	    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
	        Authentication authentication = event.getAuthentication();
	        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            String username = userDetails.getUsername();
				//logger.info("Adding student: {}", student.getStudentId());

				logger.info("User '" + username + "' successfully authenticated.");
	          
	          // Additional logic here, such as updating user information or sending notifications
				//logger.info("Adding student: {}", student.getStudentId());

				logger.info("Login successful");
	        }
	    }
}
