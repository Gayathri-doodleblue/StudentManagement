package com.project.projectcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.project.model.Attendance;
import com.project.model.Student;
import com.project.projectservice.AttendenceService;
@RestController
public class AttendenceController {
	
	@Autowired
	private AttendenceService attendenceService;
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value ="/import-AttendenceExcel")
	public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file) {
        HttpStatus status = HttpStatus.OK;

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet worksheet = workbook.getSheetAt(0);

            List<Attendance> savedAttendance = attendenceService.importAttendenceFromExcel(worksheet);

            return new ResponseEntity<>("Attendance data imported successfully", status);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading the Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
	
	
//	  @PostMapping("/send")
//	    public String sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file, String to, String[] cc, String subject, String body) {
//	        return attendenceService.sendMail(file, to, cc, subject, body);
//	    }
	
	
//	@PostMapping("/send-reports")
//    public String sendEmailReports(MultipartFile file, String subject, String body) {
//        // Parse the Excel file and retrieve attendance data
//        List<Attendance> attendanceList = parseAttendanceFile(file);
//
//        // Create a map of student names and email addresses
//        Map<String, String> studentEmailMap = createStudentEmailMap();
//
//        // Send email reports to each student's email
//        attendenceService.sendEmailReports(attendanceList, studentEmailMap, subject, body);
//
//        return "Email reports sent successfully.";
//    }

	@PostMapping("/send")
	public String sendEmailReports() {
		try {
			// Get the attendance data from the database
			List<Attendance> attendanceList = attendenceService.getAllAttendance();
			
			// Create a map of student names and email addresses
			Map<String, String> studentEmailMap = createStudentEmailMap();
			
			// Define the subject and body of the email
			String subject = "Weekly Attendance Report";
			String body = "Please find attached the weekly attendance report.";
			
			// Send email reports to each student
			attendenceService.sendEmailReports(attendanceList, studentEmailMap, subject, body);
			
			return "Email reports sent successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send email reports";
		}
	}
	
	
	private Map<String, String> createStudentEmailMap() {
	    List<Student> students = attendenceService.getAllStudents();
	    Map<String, String> studentEmailMap = new HashMap<>();

	    for (Student student : students) {
	        String studentName = student.getFirstName();
	        String studentEmail = student.getEmail();

	        studentEmailMap.put(studentName, studentEmail);
	    }

	    return studentEmailMap;
	}


}
