/**
 * 
 */
package com.wibmo.bean;

/**
 * Table Name = user_details
 * Primary Key(userId) Auto Increment
 */
public class User {
	
	private Integer userId;			// user_id
	private String userName;		// name
	private String userAddress;		// address
	private String userEmail;		// email
	private String userType;		// type
	private Integer userNumber;		// number
	
	public User() {}
	
	public User( Integer id, String type) {
		this.userType = type;	
		this.userId = id;
	}
	

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the userNumber
	 */
	public Integer getUserNumber() {
		return userNumber;
	}


	/**
	 * @param userNumber the userNumber to set
	 */
	public void setUserNumber(Integer userNumber) {
		this.userNumber = userNumber;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}
	/**
	 * @param userAddress the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
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
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}


	@Override
	public String toString() {
		return "User [userName=" + userName + ", userId=" + userId + ", userAddress=" + userAddress + ", userEmail="
				+ userEmail + ", userType=" + userType + "]";
	}
	
}
