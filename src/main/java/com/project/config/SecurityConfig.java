package com.project.config;


import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.filter.JwtFilter;
import com.project.publisher.CustomAuthenticationEventPublisher;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;



//import com.project.publisher.AuthenticationEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;


	@Bean
	public UserDetailsService userDetailsService() {

		return new UserInfoUserDetailsService();
	}
	private final CustomAuthenticationEventPublisher eventPublisher;

    public SecurityConfig(CustomAuthenticationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
//    private SecurityScheme createAPIKeyScheme() {
//        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
//            .bearerFormat("JWT")
//            .scheme("bearer");
//    }
	private static final String[] AUTH_WHITELIST = {

            

            // for Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/auth/**",
            "/swagger-ui.html",
            
            "/addUser","/excel"
           ,"/generateToken"
    };
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers(AUTH_WHITELIST).permitAll().and()
				

				
				.authorizeHttpRequests().requestMatchers("/student/**","/class/**","/subject/**","/import-excel","/classexcel","/subjectexcel","/export-excel","/export-excel/{classId}","/import-AttendenceExcel","/send").authenticated().and()
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
	                }).*/and().httpBasic().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authenticationProvider(authenticationProvider())
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
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
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	 
	 
	 
	 
	 private SecurityScheme createAPIKeyScheme() {
		    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
		        .bearerFormat("JWT")
		        .scheme("bearer");
		}
	 
	 
	 @Bean
	 public OpenAPI openAPI() {
	     return new OpenAPI().addSecurityItem(new SecurityRequirement().
	             addList("Bearer Authentication"))
	         .components(new Components().addSecuritySchemes
	             ("Bearer Authentication", createAPIKeyScheme()))
	         .info(new Info().title("My REST API")
	             .description("Some custom description of API.")
	             .version("1.0")
	             );
	 }
	  
}




