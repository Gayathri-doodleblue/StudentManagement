package com.project.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.config.UserInfoUserDetailsService;
import com.project.service.JwtService;

//import com.example.jwtapp.conig.UserInfoUserDetailsService;
//import com.example.jwtapp.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoUserDetailsService userInfoService;

//	public JwtFilter(JwtService jwtService2) {
//		// TODO Auto-generated constructor stub
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userInfoService.loadUserByUsername(username);
			if (jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(request, response);
	}

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//	}

}


