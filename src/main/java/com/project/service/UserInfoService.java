package com.project.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.exception.ServiceException;
import com.project.model.Classes;
import com.project.model.Student;
import com.project.model.UserInfo;

import com.project.repository.StudentRepository;
import com.project.repository.UserInfoRepository;



@Service
public class UserInfoService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	
	
	@Autowired
private PasswordEncoder passwordEncoder;
//	
	public UserInfo addLoginDetails(UserInfo userInfo) {
		try {

	    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	    return userInfoRepository.save(userInfo);
	}catch (Exception e) {
		throw new ServiceException("610", "Failed to save user. Please provide proper details.");
	}
}
	public Optional<UserInfo> getUserInfoByUsername(String username) {
		
		try {
	    return userInfoRepository.findByUserName(username);
	}catch (Exception e) {
		throw new ServiceException("611", "Enter proper username to finf the existing user");
	}
	}
	
	
	  
	   
	   
}
