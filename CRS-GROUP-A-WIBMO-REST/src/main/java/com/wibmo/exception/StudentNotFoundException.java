package com.wibmo.exception;

public class StudentNotFoundException extends Exception {

	private Long studentId;
	
	public StudentNotFoundException(Long studentId) {
		this.studentId = studentId;
	}
	
	@Override
	public String getMessage() {
		return "Student with Id = " + studentId + " NOT Found.";
	}
}
