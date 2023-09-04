package com.wibmo.exception;

/**
 * 
 */
public class StudentNotRegisteredForSemesterException extends Exception {

	private Integer studentId;
	private Integer semester;
	
	public StudentNotRegisteredForSemesterException(
			Integer studentId, Integer semester) {
		this.studentId = studentId;
		this.semester = semester;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " NOT Registered for Semester: " + semester + ".";
	}
}
