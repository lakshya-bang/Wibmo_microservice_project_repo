/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseIdCannotBeEmptyException extends Exception {

	@Override
	public String getMessage() {
		return "Field courseId cannot be empty.";
	}
}
