package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.exception.ServiceException;
import com.project.model.Classes;
import com.project.model.Subject;
import com.project.repository.ClassRepository;
import com.project.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	public Subject saveSubject(Subject subject)
	{
		try {
		return subjectRepository.save(subject);
	} catch (Exception e) {
        throw new ServiceException("607", "Failed to save subject. Please provide proper details.");
	}
    }

	public Subject updateSubject(Subject subject) {
		try {
			Subject existingSubject = subjectRepository.findById(subject.getSubjectId())
					.orElseThrow(() -> new IllegalArgumentException("Class not found with id "));
			existingSubject.setSubjectName(subject.getSubjectName());
			return subjectRepository.save(existingSubject);
		} catch (Exception e) {
			throw new ServiceException("608", "Enter proper subjectId to update the existing subject");
		}
	}

	public List<Subject> getSubjects() {
		try {
			return subjectRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException("608", "Failed to get the students");
		}
	}

	public void deleteSubject(int subjectId) {
		try {
			subjectRepository.deleteById(subjectId);
		} catch (ServiceException e) {
			throw new ServiceException("608", "Enter proper subjectID to delete the existing subject");
		}
	}

}
