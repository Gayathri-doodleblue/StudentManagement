package com.project.dto;

import com.project.model.Student;
import com.project.model.UserInfo;

public class AuthorizationUtils {
	public boolean isAuthorizedStudent(Student student, String username, UserInfo userInfo) {
	        return student.getFirstName().equals(userInfo.getUserName()) && student.getFirstName().equals(username);
	    }
	
	
//	public boolean isAuthorizedAdmin(Admin admin, String username, UserInfo userInfo) {
//        return admin.getAdminName().equals(userInfo.getUserName()) && admin.getAdminName().equals(username);
//    }
	
//	public boolean isAuthorizedAdmin(String adminName, UserInfo userInfo) {
//	    return userInfo.getUserName().equals(adminName);
//	}

}

