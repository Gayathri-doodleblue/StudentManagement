package com.project.projectservice;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.model.Attendance;
import com.project.model.Classes;

import com.project.model.Student;

import com.project.repository.AttendenceRepository;
import com.project.repository.StudentRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
@Service
public class AttendenceService {
	
	@Autowired
	private AttendenceRepository attendenceRepository;
	
	@Autowired
	   private  ClassService1 classService;
	@Autowired
	   private  StudentService1 studentService;
	@Autowired
	  private StudentRepository studentRepository;
	
	public List<Attendance> importAttendenceFromExcel(Sheet worksheet) {
        List<Attendance> attendenceList = new ArrayList<>();

        for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
            Row row = worksheet.getRow(index);

            // Create and set Student entity
            Attendance attendance = new Attendance();
            attendance.setId(((int) row.getCell(0).getNumericCellValue()));
            attendance.setAttendanceStatus((row.getCell(3).getStringCellValue()));
           

            attendance.setAttendanceDate(row.getCell(4).getDateCellValue());
            

            // Get or create the Class entity
            String classname = row.getCell(1).getStringCellValue();
            Classes classes = classService.getClassByName(classname);
            if (classes == null) {
                // Class doesn't exist, create a new one or handle the error accordingly
                throw new IllegalArgumentException("Class with name " + classname + " not found");
            }

            attendance.setClasses(classes);
            
            
            String studentname = row.getCell(2).getStringCellValue();
            Student student = studentService.getStudentByName(studentname);
            if (student == null) {
                // Class doesn't exist, create a new one or handle the error accordingly
                throw new IllegalArgumentException("Student with name " + studentname + " not found");
            }

            attendance.setStudent(student);

            
            attendenceList.add(attendance);
        }

        // Save studentList to the database
        return attendenceRepository.saveAll(attendenceList);
    }
	
	 @Value("${spring.mail.username}")
	    private String fromEmail;

	    @Autowired
	    private JavaMailSender javaMailSender;

//	    public String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
//	        try {
//	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//	            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//	            mimeMessageHelper.setFrom(fromEmail);
//	            mimeMessageHelper.setTo(to);
//	            mimeMessageHelper.setCc(cc);
//	            mimeMessageHelper.setSubject(subject);
//	            mimeMessageHelper.setText(body);
//
//	            for (int i = 0; i < file.length; i++) {
//	                mimeMessageHelper.addAttachment(
//	                        file[i].getOriginalFilename(),
//	                        new ByteArrayResource(file[i].getBytes()));
//	            }
//
//	            javaMailSender.send(mimeMessage);
//
//	            return "mail send";
//
//	        } catch (Exception e) {
//	            throw new RuntimeException(e);
//	        }
//
//
//	    }
//	
//	    public void sendEmailReports(List<Attendance> attendanceList, Map<String, String> studentEmailMap, String subject,
//				String body) {
//			for (Attendance attendance : attendanceList) {
//				String studentName = attendance.getStudent().getFirstName();
//				String studentEmail = studentEmailMap.get(studentName);
//				
//				if (studentEmail != null) {
//					try {
//						Workbook workbook = createAttendanceWorkbook(attendanceList, studentName);
//						
//						ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//						workbook.write(outputStream);
//						byte[] attachmentBytes = outputStream.toByteArray();
//						
//						sendEmailWithAttachment(studentEmail, subject, body, attachmentBytes);
//					} catch (IOException e) {
//						e.printStackTrace();
//						// Handle exception appropriately
//					}
//				}
//			}
//		}
//		
//		private Workbook createAttendanceWorkbook(List<Attendance> attendanceList, String studentName) {
//			Workbook workbook = new XSSFWorkbook();
//			Sheet sheet = workbook.createSheet("Attendance");
//			
//			Row headerRow = sheet.createRow(0);
//			headerRow.createCell(0).setCellValue("ID");
//			headerRow.createCell(1).setCellValue("Class Name");
//			headerRow.createCell(2).setCellValue("Student Name");
//			headerRow.createCell(3).setCellValue("Status");
//			headerRow.createCell(4).setCellValue("Date");
//			
//			int rowIndex = 1;
//			for (Attendance attendance : attendanceList) {
//				if (attendance.getStudent().getFirstName().equals(studentName)) {
//					Row dataRow = sheet.createRow(rowIndex++);
//					dataRow.createCell(0).setCellValue(attendance.getId());
//					dataRow.createCell(1).setCellValue(attendance.getClasses().getClassName());
//					dataRow.createCell(2).setCellValue(attendance.getStudent().getFirstName());
//					dataRow.createCell(3).setCellValue(attendance.getAttendanceStatus());
//					
//					Cell dateCell = dataRow.createCell(4);
//					dateCell.setCellValue(attendance.getAttendanceDate());
//					CellStyle cellStyle = workbook.createCellStyle();
//					cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd-MM-yyyy"));
//					dateCell.setCellStyle(cellStyle);
//				}
//			}
//			
//			return workbook;
//		}
//		
//		private void sendEmailWithAttachment(String recipientEmail, String subject, String body, byte[] attachmentBytes) {
//			try {
//				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//				
//				mimeMessageHelper.setFrom(fromEmail);
//				mimeMessageHelper.setTo(recipientEmail);
//				mimeMessageHelper.setSubject(subject);
//				mimeMessageHelper.setText(body);
//				
//				ByteArrayResource attachmentResource = new ByteArrayResource(attachmentBytes);
//				mimeMessageHelper.addAttachment("Attendence.xlsx", attachmentResource);
//				
//				javaMailSender.send(mimeMessage);
//			} catch (MessagingException e) {
//				e.printStackTrace();
//				// Handle exception appropriately
//			}
//		}
//		
		public List<Student> getAllStudents() {
			return studentRepository.findAll();
		}

		public List<Attendance> getAllAttendance() {
			return attendenceRepository.findAll();
			
		}

		
		public void sendEmailReports(List<Attendance> attendanceList, Map<String, String> studentEmailMap, String subject,
		        String body) {
		    Map<String, List<Attendance>> attendanceByStudent = new HashMap<>();

		    // Group attendance records by student
		    for (Attendance attendance : attendanceList) {
		        String studentName = attendance.getStudent().getFirstName();
		        attendanceByStudent.computeIfAbsent(studentName, k -> new ArrayList<>()).add(attendance);
		    }

		    // Send email reports to each student
		    for (String studentName : attendanceByStudent.keySet()) {
		        String studentEmail = studentEmailMap.get(studentName);
		        if (studentEmail != null) {
		            try {
		                Workbook workbook = createAttendanceWorkbook(attendanceByStudent.get(studentName), studentName);

		                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		                workbook.write(outputStream);
		                byte[] attachmentBytes = outputStream.toByteArray();

		                sendEmailWithAttachment(studentEmail, subject, body, attachmentBytes);
		            } catch (IOException e) {
		                e.printStackTrace();
		                // Handle exception appropriately
		            }
		        }
		    }
		}
		private Workbook createAttendanceWorkbook(List<Attendance> attendanceList, String studentName) {
		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("Attendance");

		    Row headerRow = sheet.createRow(0);
		    //headerRow.createCell(0).setCellValue("ID");
		    headerRow.createCell(1).setCellValue("Class Name");
		    headerRow.createCell(2).setCellValue("Student Name");
		    headerRow.createCell(3).setCellValue("Status");
		    headerRow.createCell(4).setCellValue("Date");

		    int rowIndex = 1;
		    for (Attendance attendance : attendanceList) {
		        Row dataRow = sheet.createRow(rowIndex++);
		       // dataRow.createCell(0).setCellValue(attendance.getId());
		        dataRow.createCell(1).setCellValue(attendance.getClasses().getClassName());
		        dataRow.createCell(2).setCellValue(attendance.getStudent().getFirstName());
		        dataRow.createCell(3).setCellValue(attendance.getAttendanceStatus());

		        Cell dateCell = dataRow.createCell(4);
		        dateCell.setCellValue(attendance.getAttendanceDate());
		        CellStyle cellStyle = workbook.createCellStyle();
		        cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd-MM-yyyy"));
		        dateCell.setCellStyle(cellStyle);
		    }

		    return workbook;
		}

	    public void sendEmailWithAttachment(String recipientEmail, String subject, String body, byte[] attachmentBytes) {
	        try {
	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	            mimeMessageHelper.setFrom(fromEmail);
	            mimeMessageHelper.setTo(recipientEmail);
	            mimeMessageHelper.setSubject(subject);
	            mimeMessageHelper.setText(body);

	            ByteArrayResource attachmentResource = new ByteArrayResource(attachmentBytes);
	            mimeMessageHelper.addAttachment("Attendencesss.xlsx", attachmentResource);

	            javaMailSender.send(mimeMessage);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            // Handle exception appropriately
	        }
	    }

}
