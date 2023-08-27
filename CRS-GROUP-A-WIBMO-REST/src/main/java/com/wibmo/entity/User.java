/**
 * 
 */
package com.wibmo.entity;

import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;

/**
 * Table name = auth_creds
 */
public class User {
	
	private Integer userId;							// user_id (PK)
	private String userEmail;						// user_email
	private UserType userType;						// user_type
	private RegistrationStatus registrationStatus;	// reg_status
	private String password;
	
	public User() {}
	
	public User(
			Integer userId, 
			String userEmail,
			RegistrationStatus registrationStatus,
			UserType userType) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.registrationStatus = registrationStatus;
		this.userType = userType;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the email
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param email the email to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the registrationStatus
	 */
	public RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}

	/**
	 * @param registrationStatus the registrationStatus to set
	 */
	public void setRegistrationStatus(RegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
