/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class StudentIdCannotBeEmptyException extends Exception {

	@Override
	public String getMessage() {
		return "Field studentId cannot be empty.";
	}
}
