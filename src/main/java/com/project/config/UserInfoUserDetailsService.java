package com.project.config;


import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.model.UserInfo;
import com.project.repository.UserInfoRepository;





@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	 @Autowired
     private UserInfoRepository userInfoRepository;

		
		 @Override
		    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
		        UserInfo userInfo = userInfoOptional.orElseThrow(() ->
		                new UsernameNotFoundException("Username not found: " + username));

		        return new UserInfoUserDetails(userInfo);
		    }
}
 

