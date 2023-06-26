package com.project.projectservice;

import com.project.model.Classes;
import com.project.model.Subject;
import com.project.repository.SubjectRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubjectService1 {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService1(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

  
    public Subject createSubject(String subjectName) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(int subjectId) {
        return subjectRepository.findById(subjectId).orElse(null);
    }
    
    
    public List<Subject> saveAll(List<Subject> subject) {
        return subjectRepository.saveAll(subject);
    }
    
    
    public List<Subject> importSubjectExcelFile(XSSFSheet worksheet) throws IOException {
        
        List<Subject> subjectList = new ArrayList<>();

       

        for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
            XSSFRow row = worksheet.getRow(index);

            // Extract class details from the Excel row
            int subjectId = (int) row.getCell(0).getNumericCellValue();
            String subjectName = row.getCell(1).getStringCellValue();

            // Create and save the Class entity
            Subject subject = new Subject(subjectId, subjectName);
            subjectList.add(subject);
        }

        // Save classList to the database
        List<Subject> savedSubjectList = subjectRepository.saveAll(subjectList);

        return savedSubjectList;
    }
}

