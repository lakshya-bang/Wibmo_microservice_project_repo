package com.wibmo.exception;

import com.wibmo.bean.Student;

public class StudentNotEligibleForRegistrationException extends Exception {

	private Student student;
	
	public StudentNotEligibleForRegistrationException(Student student) {
		this.student = student;
	}
	
	@Override
	public String getMessage() {
		return student + " is not eligible for Registration. Kindly contact Admin.";
	}
}
