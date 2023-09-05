/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class UserNotApprovedException extends Throwable{

	private String userEmail;
	public UserNotApprovedException(String userEmail) {
		this.userEmail = userEmail;
	}
	@Override
	public String getMessage() {
		return "Your account details are yet to be approved by the Admin.";
	}
}
