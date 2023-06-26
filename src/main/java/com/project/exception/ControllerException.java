package com.project.exception;

import org.springframework.stereotype.Component;

@Component
public class ControllerException extends Exception {
	private String errorcode;
	private String errormessage;
	public ControllerException() {
		
	}
	public ControllerException(String errorcode, String errormessage) {
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



