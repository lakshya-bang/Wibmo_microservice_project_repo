/**
 * 
 */
package com.wibmo.bean;

/**
 * 
 */
public class User {
	private String userName;
	private Long userId;
	private String userAddress;
	private String userEmail;
	private String userType;
	private Long userNumber;
	
	public User(String name, Long id, String address, String email, String type, Long number) {
		this.userName = name;
		this.userEmail = email;
		this.userAddress = address;
		this.userType = type;	
		this.userId = id;
		this.userNumber = number;
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
	public Long getUserNumber() {
		return userNumber;
	}


	/**
	 * @param userNumber the userNumber to set
	 */
	public void setUserNumber(Long userNumber) {
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
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
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
}
