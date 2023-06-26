package com.project.controller;


import com.project.dto.ReportCardStudentDto;
import com.project.dto.ReportCardSubjectDto;
import com.project.exception.ControllerException;
import com.project.exception.ServiceException;
import com.project.exception.ValidationException;
import com.project.model.ReportCard;
import com.project.service.ReportCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/student")

public class ReportCardController {
	
	Logger logger = LoggerFactory.getLogger(ReportCardController.class);
	
	@Autowired
	private ValidationException validationException;

    private final ReportCardService reportCardService;

    @Autowired
    public ReportCardController(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    @PostMapping("/addReportCard")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addReportCard(@RequestBody ReportCardStudentDto reportCardDto) {
        try {
            logger.info("Adding report card for student: {}", reportCardDto.getStudentId());
            reportCardService.addReportCard(reportCardDto);
            return ResponseEntity.ok("Report card added successfully");
        }  catch (ServiceException e) {
            logger.error("ServiceException occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException("609", "An unexpected error occurred");
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   

   
    
    @PutMapping("/update/{reportCardId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateReportCard(@PathVariable int reportCardId, @RequestBody ReportCardStudentDto reportCardDto) {
        try {
            logger.info("Updating report card with ID: {}", reportCardId);
            ReportCardSubjectDto reportCardSubjectDto = reportCardDto.getSubjects().get(0);
            reportCardService.updateReportCard(reportCardId, reportCardDto, reportCardSubjectDto);
            return ResponseEntity.ok("Report card updated successfully");
        }  catch (ServiceException e) {
            //log.error("ServiceException occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException(e.getErrorcode(), e.getErrormessage());
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ///log.error("An unexpected error occurred: {}", e.getMessage());
            ControllerException ce = new ControllerException("609", "An unexpected error occurred");
            return validationException.handleControllerException(ce, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

   

}

