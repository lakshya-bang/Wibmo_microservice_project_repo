package com.wibmo.exception;

/**
 * 
 */
public class StudentNotEligibleForCourseRegistrationException extends Exception {

	private Integer studentId;
	
	public StudentNotEligibleForCourseRegistrationException(Integer studentId) {
		this.studentId = studentId;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " NOT eligible for Course Registration.";
	}
}
