package com.project.projectcontroller;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.model.Classes;
import com.project.model.Subject;
import com.project.projectservice.ClassService1;
import com.project.projectservice.SubjectService1;

@RestController
public class SubjectExcel {


    private final SubjectService1 subjectService;

    @Autowired
    public SubjectExcel(SubjectService1 subjectService) {
        this.subjectService = subjectService;
    }

   
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/subjectexcel")
    public ResponseEntity<String> importClassExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        HttpStatus status = HttpStatus.OK;
        //List<Subject> subjectList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			XSSFSheet worksheet = workbook.getSheetAt(0);

			List<Subject> savedStudents = subjectService.importSubjectExcelFile(worksheet);

			return new ResponseEntity<>("Subject data imported successfully", status);
		} catch (IOException e) {
			return new ResponseEntity<>("Error reading the Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
