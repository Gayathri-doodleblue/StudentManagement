package com.project.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.exception.ServiceException;
import com.project.model.Classes;
import com.project.model.Student;
import com.project.repository.ClassRepository;

@Service
public class ClassService {
	
	@Autowired
	private ClassRepository classRepository;
	
	public Classes saveClass(Classes classes) {
	    try {
	        return classRepository.save(classes);
	    } catch (Exception e) {
	        throw new ServiceException("601", "Failed to save class. Please provide proper details.");
	    }
	}

	public Classes updateClass(Classes classes) {
	    try {
	        Classes existingClass = classRepository.findById(classes.getClassId())
	                .orElseThrow(() -> new ClassNotFoundException("Class not found with id " + classes.getClassId()));
	        
	        existingClass.setClassName(classes.getClassName());
	        
	   
	        
	        return classRepository.save(existingClass);
	    
	    } catch (Exception e) {
	        throw new ServiceException("602", "Enter proper classId to update the existing class");
	    }
	}

	public void deleteClass(int classId) {
	    try {
	        classRepository.deleteById(classId);
	   
	    } catch (ServiceException e) {
	        throw new ServiceException("602", "Enter proper classId to delete the existing class");
	    }
	}


//	  public Classes getClassById(int classId) {
//	        return classRepository.findById(classId)
//	                .orElseThrow(() -> new NoSuchElementException("Class not found"));
//	    }

}
