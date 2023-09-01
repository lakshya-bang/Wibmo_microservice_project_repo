package com.wibmo.exception;

public class UserNotAuthorizedForLogIn extends Exception{
	String email;
	
	public UserNotAuthorizedForLogIn(String email) {
		this.email = email;
	}
	
	@Override
	public String getMessage() {
		return "User with Email: " + email + " is Not Approved for LogIn.";
	}
}
