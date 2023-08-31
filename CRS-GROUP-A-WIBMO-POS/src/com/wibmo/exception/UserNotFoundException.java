package com.wibmo.exception;

public class UserNotFoundException extends Exception {

	private Integer userId;
	
	public UserNotFoundException(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String getMessage() {
		return "User with Id = " + userId + " NOT Found.";
	}
}
