package com.project.projectservice;

import com.project.model.ReportCard;
import com.project.model.Student;
import com.project.repository.ReportCardRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCardService1 {

    private final ReportCardRepository reportCardRepository;

    @Autowired
    public ReportCardService1(ReportCardRepository reportCardRepository) {
        this.reportCardRepository = reportCardRepository;
    }

    public ReportCard saveReportCard(ReportCard reportCard) {
        return reportCardRepository.save(reportCard);
    }

//	public List<ReportCard> getReportCardByStudent(Student student) {
//		 return reportCardRepository.findByStudent(student);
//	}
	
	 public List<ReportCard> getReportCardsByStudent(Student student) {
	        // Assuming you have a repository or DAO for ReportCard
	        return reportCardRepository.findByStudent(student);
	    }
}
