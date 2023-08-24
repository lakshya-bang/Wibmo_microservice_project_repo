package com.wibmo.exception;

public class UserNotFoundException extends Exception {

	private Long studentId;
	
	public UserNotFoundException(Long studentId) {
		this.studentId = studentId;
	}
	
	@Override
	public String getMessage() {
		return "Student with Id = " + studentId + " NOT Found.";
	}
}
