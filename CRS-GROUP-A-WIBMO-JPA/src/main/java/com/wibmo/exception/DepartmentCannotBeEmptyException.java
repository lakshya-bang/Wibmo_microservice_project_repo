package com.wibmo.exception;

/**
 * Throws exception when the expected field: department
 * is received as null or empty from the Client.
 */
public class DepartmentCannotBeEmptyException extends Exception {
	
	public DepartmentCannotBeEmptyException() {}
	
	@Override
	public String getMessage() {
		return "Field Department cannot be Empty!";
	}
}
