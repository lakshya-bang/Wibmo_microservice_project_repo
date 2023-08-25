/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class DepartmentCannotBeEmptyException extends Exception {
	
	public DepartmentCannotBeEmptyException() {}
	
	@Override
	public String getMessage() {
		return "Field Department cannot be Empty!";
	}
}
