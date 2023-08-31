package com.wibmo.exception;

public class SemesterCannotBeEmptyException extends Exception {

	public SemesterCannotBeEmptyException() {}
	
	@Override
	public String getMessage() {
		return "Field Semester cannot be Empty!";
	}
	
}
