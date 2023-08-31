package com.wibmo.exception;

/**
 * 
 */
public class GradeValueInvalidException extends Exception {

	@Override
	public String getMessage() {
		return "Field grade does not have a valid value.";
	}
}
