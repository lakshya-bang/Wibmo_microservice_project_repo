package com.wibmo.exception;

import com.wibmo.enums.UserType;

public class UserNotFoundException extends Exception {

	private Integer id;
	private UserType userType;
	
	public UserNotFoundException(Integer id, UserType userType) {
		this.id = id;
		this.userType = userType;
	}
	
	@Override
	public String getMessage() {
		return userType.toString() + " with Id = " + id + " NOT Found.";
	}
}
