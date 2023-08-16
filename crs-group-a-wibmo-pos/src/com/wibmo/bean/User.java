/**
 * 
 */
package com.wibmo.bean;

import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;

/**
 * Table name = "auth_creds"
 */
public class User {
	
	private Integer userId;							// user_id (PK)
	private UserType userType;						// user_type
	private RegistrationStatus registrationStatus;	// reg_status
	
	public User() {}
	
	public User(
			Integer userId, 
			RegistrationStatus registrationStatus,
			UserType userType) {
		this.userId = userId;
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
}
