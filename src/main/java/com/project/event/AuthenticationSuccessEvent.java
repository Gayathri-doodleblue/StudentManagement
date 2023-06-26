package com.project.event;
import org.springframework.context.ApplicationEvent;

public class AuthenticationSuccessEvent  {

    private final String username;

    public AuthenticationSuccessEvent(String username) {
       
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
