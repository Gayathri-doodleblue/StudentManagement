package com.project.projectservice;

import com.project.controller.ReportCardController;
import com.project.exception.ServiceException;
import com.project.model.Classes;
import com.project.model.ReportCard;
import com.project.model.Student;
import com.project.model.Subject;
import com.project.repository.ClassRepository;
import com.project.repository.StudentRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;



@Service
public class StudentService1 {
	@Autowired
	private ReportCardService1 reportCardService;
	@Autowired
	   private  ClassService1 classService;
	@Autowired
	    private  SubjectService1 subjectService;
	
	@Autowired
	private ClassRepository classRepository;
	
	Logger logger = LoggerFactory.getLogger(StudentService1.class);

    public Classes createClass(String className) {
        Classes classes = new Classes();
        classes.setClassName(className);
        return classRepository.save(classes);
    }

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService1(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> saveAll(List<Student> students) {
        return studentRepository.saveAll(students);
    }
    
    public List<Student> getStudentsByClass(int classes) {
        return studentRepository.findByClassesClassId(classes);
    }

//	public List<Student> getStudentsByClass(int classes) {
//		return studentRepository.findByClassesClassId(classes);
//		
//	}

    
    public void generateExcel(HttpServletResponse response) throws Exception {

		List<Student> students = studentRepository.findAll();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Student Info");
		Row row = sheet.createRow(0);

		row.createCell(0).setCellValue("StudentID");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("Address");
		row.createCell(3).setCellValue("Phoneno");
		row.createCell(2).setCellValue("classId");



		int dataRowIndex = 1;

		for (Student student : students) {
			Row dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(student.getStudentId());
			dataRow.createCell(1).setCellValue(student.getFirstName());
			dataRow.createCell(2).setCellValue(student.getAddress());
			dataRow.createCell(3).setCellValue(student.getPhoneNumber());
			//dataRow.createCell(4).setCellTypeImpl(student.getClasses());
			dataRowIndex++;
		}

		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();

	}

	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentRepository.findAll();
	}
	
	
	public void exportExcelStudentFile(HttpServletResponse response) throws IOException {
        List<Student> students = studentRepository.findAll();

        // Create the Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student Name");
        headerRow.createCell(1).setCellValue("Class");
        headerRow.createCell(2).setCellValue("Subject");
        headerRow.createCell(3).setCellValue("Marks");

        // Populate data rows
        int rowNum = 1;
        for (Student student : students) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(student.getFirstName() + " " + student.getLastName());
            dataRow.createCell(1).setCellValue(student.getClasses().getClassName());

            // Retrieve the report cards for the student
            List<ReportCard> reportCards = reportCardService.getReportCardsByStudent(student);
            for (ReportCard reportCard : reportCards) {
                dataRow.createCell(2).setCellValue(reportCard.getSubject().getSubjectName());
                dataRow.createCell(3).setCellValue(reportCard.getMarks());
                dataRow = sheet.createRow(rowNum++);
            }
        }

        ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();   
    }
	
	
	public void exportClassFileById(int classId,HttpServletResponse response) throws IOException {
        

        // Retrieve the students for the specified class
        List<Student> students = studentRepository.findByClassesClassId(classId);

        // Create the Excel workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student Name");
        headerRow.createCell(1).setCellValue("Class");
        headerRow.createCell(2).setCellValue("Subject");
        headerRow.createCell(3).setCellValue("Marks");

        // Populate data rows
        int rowNum = 1;
        for (Student student : students) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(student.getFirstName() + " " + student.getLastName());
            dataRow.createCell(1).setCellValue(student.getClasses().getClassName());

            // Retrieve the report cards for the student
            List<ReportCard> reportCards = reportCardService.getReportCardsByStudent(student);
            for (ReportCard reportCard : reportCards) {
                dataRow.createCell(2).setCellValue(reportCard.getSubject().getSubjectName());
                dataRow.createCell(3).setCellValue(reportCard.getMarks());
                dataRow = sheet.createRow(rowNum++);
            }
        }


        // Write the workbook to the response output stream
        ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close(); 
    }
	
	
	
	public List<Student> importStudentsFromExcel(Sheet worksheet) {
        List<Student> studentList = new ArrayList<>();

        for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
            Row row = worksheet.getRow(index);

            // Create and set Student entity
            Student student = new Student();
            student.setStudentId((int) row.getCell(0).getNumericCellValue());
            student.setFirstName(row.getCell(1).getStringCellValue());
            student.setLastName(row.getCell(2).getStringCellValue());
            student.setAddress(row.getCell(3).getStringCellValue());
            student.setPhoneNumber((long) row.getCell(4).getNumericCellValue());

            // Get or create the Class entity
            int classId = (int) row.getCell(5).getNumericCellValue();
            Classes classes = classService.getClassById(classId);
            if (classes == null) {
                // Class doesn't exist, create a new one or handle the error accordingly
                throw new IllegalArgumentException("Class with ID " + classId + " not found");
            }

            student.setClasses(classes);

            int subjectId = (int) row.getCell(9).getNumericCellValue();
            Subject subject = subjectService.getSubjectById(subjectId);
            if (subject == null) {
                // Handle if the subject is not found
                throw new IllegalArgumentException("Subject with ID " + subjectId + " not found");
            }

            // Create and set the ReportCard entity
            ReportCard reportCard = new ReportCard();
            reportCard.setId((int) row.getCell(6).getNumericCellValue());
            reportCard.setMarks((long) row.getCell(7).getNumericCellValue());
            reportCard.setStudent(student);
            reportCard.setSubject(subject);

            // Save the ReportCard
            reportCardService.saveReportCard(reportCard);

            studentList.add(student);
        }

        // Save studentList to the database
        return studentRepository.saveAll(studentList);
    }
	
	
	
       
}

