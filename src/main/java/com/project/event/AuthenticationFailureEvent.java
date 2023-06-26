package com.project.event;
import org.springframework.context.ApplicationEvent;

public class AuthenticationFailureEvent {

    private final String username;

    public AuthenticationFailureEvent(String username) {
       
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
