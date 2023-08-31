package com.wibmo.exception;

import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
public class CannotAddGradeStudentRegistrationNotApprovedException extends Exception {

	private Integer studentId;
	private RegistrationStatus registrationStatus;
	
	public CannotAddGradeStudentRegistrationNotApprovedException(
			Integer studentId, RegistrationStatus registrationStatus) {
		this.studentId = studentId;
		this.registrationStatus = registrationStatus;
	}
	
	@Override
	public String getMessage() {
		return "Cannot Add Grade! Student Id: " + studentId + " Course Registration Status is " + registrationStatus.toString() + ".";
	}
}
