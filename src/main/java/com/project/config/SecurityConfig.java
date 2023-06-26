package com.project.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.publisher.CustomAuthenticationEventPublisher;

//import com.project.publisher.AuthenticationEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {

		return new UserInfoUserDetailsService();
	}
	private final CustomAuthenticationEventPublisher eventPublisher;

    public SecurityConfig(CustomAuthenticationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
	private static final String[] AUTH_WHITELIST = {

            

            // for Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            
            "/addUser","/excel"
           
    };
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers(AUTH_WHITELIST).permitAll().and()
				

				
				.authorizeHttpRequests().requestMatchers("/student/**","/class/**","/subject/**","/import-excel","/classexcel","/subjectexcel","/export-excel","/export-excel/{classId}").authenticated().and()
			      .formLogin()./*.successHandler((request, response, authentication) -> {
	                    eventPublisher.publishAuthenticationSuccessEvent();
	                    // Additional success handling if needed
	                    response.sendRedirect("/success");
	                })
	                .failureHandler((request, response, exception) -> {
	                    String username = request.getParameter("username");
	                    eventPublisher.publishAuthenticationFailureEvent(username);
	                    // Additional failure handling if needed
	                    response.sendRedirect("/error");
	                }).*/and().httpBasic().and().build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}
