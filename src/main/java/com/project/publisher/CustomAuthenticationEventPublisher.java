package com.project.publisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.project.event.AuthenticationFailureEvent;
import com.project.event.AuthenticationSuccessEvent;

@Component
public class CustomAuthenticationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public CustomAuthenticationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishAuthenticationSuccessEvent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(username);
        eventPublisher.publishEvent(event);
    }

    public void publishAuthenticationFailureEvent(String username) {
        AuthenticationFailureEvent event = new AuthenticationFailureEvent(username);
        eventPublisher.publishEvent(event);
    }
}
