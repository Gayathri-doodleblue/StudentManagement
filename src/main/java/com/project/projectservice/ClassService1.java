package com.project.projectservice;

import com.project.model.Classes;
import com.project.model.Student;
import com.project.repository.ClassRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
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
public class ClassService1 {

	@Autowired
	private ClassRepository classRepository;

    public Classes createClass(String className) {
        Classes classes = new Classes();
        classes.setClassName(className);
        return classRepository.save(classes);
    }

    public Classes getClassById(int classId) {
        return classRepository.findById(classId).orElse(null);
    }
    public void saveClass(Classes classes) {
        classRepository.save(classes);
    }
    
    
    public List<Classes> saveAll(List<Classes> classes) {
        return classRepository.saveAll(classes);
    }
    
    public List<Classes> getClassBy() {
        return classRepository.findAll();
    }
    
    
    
    
    
    public List<Classes> importClassExcelFile(XSSFSheet worksheet) throws IOException {
        
        List<Classes> classList = new ArrayList<>();

        
        for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
            XSSFRow row = worksheet.getRow(index);

            // Extract class details from the Excel row
            int classId = (int) row.getCell(0).getNumericCellValue();
            String className = row.getCell(1).getStringCellValue();

            // Create and save the Class entity
            Classes classes = new Classes(classId, className);
            classList.add(classes);
        }

        // Save classList to the database
        List<Classes> savedClasses = classRepository.saveAll(classList);

       return savedClasses;
    }

	public Classes getClassByName(String classname) {
		return  classRepository.findByClassName(classname);
		
	}
}