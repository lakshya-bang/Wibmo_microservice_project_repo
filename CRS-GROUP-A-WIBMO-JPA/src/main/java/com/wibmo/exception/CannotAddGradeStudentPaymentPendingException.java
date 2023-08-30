package com.wibmo.exception;

/**
 * 
 */
public class CannotAddGradeStudentPaymentPendingException extends Exception {

	private Integer studentId;
	private Integer semester;
	
	public CannotAddGradeStudentPaymentPendingException(Integer studentId, Integer semester) {
		this.studentId = studentId;
		this.semester = semester;
	}
	
	@Override
	public String getMessage() {
		return "Cannot Add Grade! Student Id: " + studentId + " has pending payment for Registration for Semester: " + semester + ".";
	}
	
}
