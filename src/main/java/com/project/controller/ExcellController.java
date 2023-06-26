//package com.project.controller;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.project.model.Classes;
//import com.project.model.Student;
//import com.project.repository.StudentRepository;
//import com.project.service.StudentService;
//
//@RestController
//public class ExcellController {
//
//    private final StudentService studentService;
//    
//    @Autowired
//	private StudentRepository studentRepository;
//
//    @Autowired
//    public ExcellController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PostMapping(value = "/import-excel")
//    public ResponseEntity<List<Student>> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
//        HttpStatus status = HttpStatus.OK;
//        List<Student> studentList = new ArrayList<>();
//
//        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//        XSSFSheet worksheet = workbook.getSheetAt(0);
//
//        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
//            if (index > 0) {
//                XSSFRow row = worksheet.getRow(index);
//
//                // Create and set Student entity
//                Student student = new Student();
//                student.setStudentId((int) row.getCell(0).getNumericCellValue());
//                student.setFirstName(row.getCell(1).getStringCellValue());
//                student.setLastName(row.getCell(2).getStringCellValue());
//                student.setAddress(row.getCell(3).getStringCellValue());
//                student.setPhoneNumber((long) row.getCell(4).getNumericCellValue());
//
//                Classes classes = new Classes();
//                classes.setClassId((int) row.getCell(5).getNumericCellValue());
//                // Set the Classes object in the Student entity
//                student.setClasses(classes);
//
//                studentList.add(student);
//            }
//        // Save studentList to the database
//        List<Student> savedStudents = studentRepository.saveAll(studentList);
//
//        return new ResponseEntity<>(savedStudents, status);
//    }
//}
//}
