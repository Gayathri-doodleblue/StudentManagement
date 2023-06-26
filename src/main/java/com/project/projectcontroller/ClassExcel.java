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
import com.project.model.Student;
import com.project.projectservice.ClassService1;

@RestController
public class ClassExcel {

	private final ClassService1 classService;

	@Autowired
	public ClassExcel(ClassService1 classService) {
		this.classService = classService;
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/classexcel")
	public ResponseEntity<String> importClassExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
		HttpStatus status = HttpStatus.OK;
		//List<Classes> classList = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			XSSFSheet worksheet = workbook.getSheetAt(0);

			List<Classes> savedStudents = classService.importClassExcelFile(worksheet);

			return new ResponseEntity<>("Class data imported successfully", status);
		} catch (IOException e) {
			return new ResponseEntity<>("Error reading the Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
