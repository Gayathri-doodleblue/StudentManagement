package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.UserInfo;



public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	//Optional<UserInfo> findByUserEmail(String username);

	Optional<UserInfo> findByUserName(String username);

}
