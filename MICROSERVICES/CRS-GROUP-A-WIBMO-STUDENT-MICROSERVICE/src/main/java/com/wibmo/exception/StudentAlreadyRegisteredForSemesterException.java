package com.wibmo.exception;

/**
 * 
 */
public class StudentAlreadyRegisteredForSemesterException extends Exception {
	
	private Integer studentId;
	private Integer semester;
	
	public StudentAlreadyRegisteredForSemesterException(Integer studentId, Integer semester) {
		this.studentId = studentId;
		this.semester = semester;
	}

	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " already registered for Sem: " + semester + ".";
	}
}
