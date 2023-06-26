package com.project.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class ValidationException {
	
	
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorMap;
//    }
	

	    @ExceptionHandler(ControllerException.class)
	    public ResponseEntity<Map<String, String>> handleControllerException(ControllerException ex,HttpStatus status) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("errorCode", ex.getErrorcode());
	        errorMap.put("errorMessage", ex.getErrormessage());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
	    }

	    @ExceptionHandler(ServiceException.class)
	    public ResponseEntity<Map<String, String>> handleServiceException(ServiceException ex,HttpStatus status) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("errorCode", ex.getErrorcode());
	        errorMap.put("errorMessage", ex.getErrormessage());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
	    }




}
