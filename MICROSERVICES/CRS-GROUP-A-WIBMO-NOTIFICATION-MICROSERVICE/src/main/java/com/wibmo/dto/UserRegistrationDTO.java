/**
 * 
 */
package com.wibmo.dto;

import com.wibmo.enums.UserType;

/**
 * 
 */
public class UserRegistrationDTO {
	
	private String name;
	private String userEmail;
	private String password;
	private UserType userType;
	private String department;
	private Integer semester;
	
	/**
	 * @return the userName
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setName(String userName) {
		this.name = userName;
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
