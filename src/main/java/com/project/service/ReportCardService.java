package com.project.service;





import com.project.dto.ReportCardStudentDto;
import com.project.dto.ReportCardSubjectDto;
import com.project.exception.ServiceException;
import com.project.model.ReportCard;
import com.project.model.Student;
import com.project.model.Subject;
import com.project.repository.ReportCardRepository;
import com.project.repository.StudentRepository;
import com.project.repository.SubjectRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCardService {

    private final ReportCardRepository reportCardRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public ReportCardService(ReportCardRepository reportCardRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.reportCardRepository = reportCardRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public void addReportCard(ReportCardStudentDto reportCardDto) {
        try {
            Student student = studentRepository.findById(reportCardDto.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student with ID " + reportCardDto.getStudentId() + " not found"));

            for (ReportCardSubjectDto subjectJson : reportCardDto.getSubjects()) {
                try {
                    Subject subject = subjectRepository.findById(subjectJson.getSubjectId())
                            .orElseThrow(() -> new IllegalArgumentException("Subject with ID " + subjectJson.getSubjectId() + " not found"));

                    ReportCard reportCard = new ReportCard();
                    reportCard.setMarks(subjectJson.getMarks());
                    reportCard.setStudent(student);
                    reportCard.setSubject(subject);

                    reportCardRepository.save(reportCard);
                } catch (Exception e) {
                    throw new ServiceException("603", "Failed to add report card , subject id is not found");
                }
            }
        } catch (Exception e) {
            throw new ServiceException("603", "Failed to add report card , sutent id is not found" );
        }
    }


    public void updateReportCard(int reportCardId, ReportCardStudentDto reportCardDto, ReportCardSubjectDto reportCardSubjectDto) {
        try {
            Optional<ReportCard> reportCardOptional = reportCardRepository.findById(reportCardId);
            if (reportCardOptional.isEmpty()) {
                throw new IllegalArgumentException("Report card with ID " + reportCardId + " not found");
            }

            ReportCard reportCard = reportCardOptional.get();
            reportCard.setMarks(reportCardSubjectDto.getMarks());

            Optional<Student> studentOptional = studentRepository.findById(reportCardDto.getStudentId());
            if (studentOptional.isEmpty()) {
                throw new IllegalArgumentException("Student with ID " + reportCardDto.getStudentId() + " not found");
            }

            Student student = studentOptional.get();
            reportCard.setStudent(student);

            Optional<Subject> subjectOptional = subjectRepository.findById(reportCardSubjectDto.getSubjectId());
            if (subjectOptional.isEmpty()) {
                throw new IllegalArgumentException("Subject with ID " + reportCardSubjectDto.getSubjectId() + " not found");
            }

            Subject subject = subjectOptional.get();
            reportCard.setSubject(subject);

            reportCardRepository.save(reportCard);
        } catch (Exception e) {
            throw new ServiceException("604", e.getMessage());
        }
    }


 
    
//    public void deleteReportCardsByStudentId(int studentId) {
//        // Delete all report cards associated with the student
//        reportCardRepository.deleteByStudentId(studentId);
//    }
//    
//    public boolean hasReportCardsByStudentId(int studentId) {
//        // Check if there are any report cards associated with the student
//        return reportCardRepository.existsByStudentId(studentId);
//    }
}
