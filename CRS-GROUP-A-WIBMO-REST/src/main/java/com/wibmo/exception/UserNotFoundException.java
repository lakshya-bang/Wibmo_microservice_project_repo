package com.wibmo.exception;

public class UserNotFoundException extends Exception {

	private Integer studentId;
	
	public UserNotFoundException(Integer professorId) {
		this.studentId = professorId;
	}
	
	@Override
	public String getMessage() {
		return "Student with Id = " + studentId + " NOT Found.";
	}
}
