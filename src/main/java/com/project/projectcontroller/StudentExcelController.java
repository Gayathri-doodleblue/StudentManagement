package com.project.projectcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.model.Classes;
import com.project.model.ReportCard;
import com.project.model.Student;
import com.project.model.Subject;
import com.project.projectservice.ClassService1;
import com.project.projectservice.ReportCardService1;
import com.project.projectservice.StudentService1;
import com.project.projectservice.SubjectService1;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class StudentExcelController {


    private final StudentService1 studentService;
    private final ClassService1 classService;
    private final SubjectService1 subjectService;
    private final ReportCardService1 reportCardService;

    @Autowired
    public StudentExcelController(StudentService1 studentService, ClassService1 classService,
                           SubjectService1 subjectService, ReportCardService1 reportCardService) {
        this.studentService = studentService;
        this.classService = classService;
        this.subjectService = subjectService;
        this.reportCardService = reportCardService;
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/import-excel")

    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file) {
        HttpStatus status = HttpStatus.OK;

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet worksheet = workbook.getSheetAt(0);

            List<Student> savedStudents = studentService.importStudentsFromExcel(worksheet);

            return new ResponseEntity<>("Student data imported successfully", status);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading the Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/export-excel/{classId}")
    public ResponseEntity<?> exportExcelFile(@PathVariable("classId") int classId, HttpServletResponse response) throws IOException {
	   response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=class.xls";

		response.setHeader(headerKey, headerValue);
		
		studentService.exportClassFileById(classId, response);
		
		response.flushBuffer();

       return ResponseEntity.ok().build();


        
    }
    
    @GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response) throws Exception{
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=students.xls";

		response.setHeader(headerKey, headerValue);
		
		studentService.generateExcel(response);
		
		response.flushBuffer();
	}
    
    
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/export-excel")
    public ResponseEntity<?> exportExcelFile(HttpServletResponse response) throws IOException {
                response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=students.xls";

		response.setHeader(headerKey, headerValue);
		
		studentService.exportExcelStudentFile(response);
		
		response.flushBuffer();

        return ResponseEntity.ok().build();
    }
}
    
