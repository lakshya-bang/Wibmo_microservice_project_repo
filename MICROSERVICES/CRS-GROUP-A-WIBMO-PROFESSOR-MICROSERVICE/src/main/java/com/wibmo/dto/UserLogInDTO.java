package com.wibmo.dto;

public class UserLogInDTO {
	String userEmail;
	String password;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserLogInDTO [userEmail=" + userEmail + ", password=" + password + "]";
	}
	
	
}
