package com.wibmo.exception;

public class UserWithEmailAlreadyExistsException extends Exception {

	private String email;
	
	public UserWithEmailAlreadyExistsException(String email) {
		this.email = email;
	}
	
	@Override
	public String getMessage() {
		return "User with Email: " + email + " already exists.";
	}
	
}
