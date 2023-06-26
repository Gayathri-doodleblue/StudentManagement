package com.project.listeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureEventListener {
	Logger logger = LoggerFactory.getLogger(AuthenticationFailureEventListener.class);
	@Async
    @EventListener
    public void handleAuthenticationFailureEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        logger.warn("Authentication failed for user '" + username + "'. Invalid credentials.");
        // Additional logic here, such as logging the failed attempt or blocking the user
    }
}
