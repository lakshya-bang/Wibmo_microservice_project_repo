package com.wibmo.dto;

import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;

/**
 * 
 */
public class UserResponseDTO {

	private Integer userId;
	private String userEmail;
	private UserType userType;
	private RegistrationStatus registrationStatus;

	public UserResponseDTO() {}
	
	public UserResponseDTO(
			Integer userId, 
			String userEmail, 
			UserType userType, 
			RegistrationStatus registrationStatus) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.userType = userType;
		this.registrationStatus = registrationStatus;
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
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

}
