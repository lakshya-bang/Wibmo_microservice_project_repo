/**
 * 
 */
package com.wibmo.dto;

import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;

/**
 * 
 */
public class UserRegistrationDetailsDTO {
	private Integer userId;
	private String userEmail;
	private String password;
	private String name;
	private RegistrationStatus regStatus;
	private UserType userType;
	private String department;
	private Integer semester;
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
	 * @return the userName
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserEmail(String userName) {
		this.userEmail = userName;
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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the regStatus
	 */
	public RegistrationStatus getRegStatus() {
		return regStatus;
	}
	/**
	 * @param regStatus the regStatus to set
	 */
	public void setRegStatus(RegistrationStatus regStatus) {
		this.regStatus = regStatus;
	}
	/**
	 * @return the userTyper
	 */
	public UserType getUserType() {
		return userType;
	}
	/**
	 * @param userTyper the userTyper to set
	 */
	public void setUserType(UserType userTyper) {
		this.userType = userTyper;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the semester
	 */
	public Integer getSemester() {
		return semester;
	}
	/**
	 * @param semester the semester to set
	 */
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
}
