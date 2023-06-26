package com.project.exception;

import org.springframework.stereotype.Component;

@Component
public class ServiceException extends RuntimeException {
	private String errorcode;
	private String errormessage;
	public ServiceException() {
		
	}
	public ServiceException(String errorcode, String errormessage) {
		super();
		this.errorcode = errorcode;
		this.errormessage = errormessage;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	
	
	

}
