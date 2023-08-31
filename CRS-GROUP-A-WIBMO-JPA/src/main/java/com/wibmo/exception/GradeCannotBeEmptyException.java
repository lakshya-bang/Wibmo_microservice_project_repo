package com.wibmo.exception;

/**
 * 
 */
public class GradeCannotBeEmptyException extends Exception {

	@Override
	public String getMessage() {
		return "Field grade cannot be empty.";
	}
}
