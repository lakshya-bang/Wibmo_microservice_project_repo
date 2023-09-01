/**
 * 
 */
package com.wibmo.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;

/**
 * 
 */

@Entity
@Table(name = "auth_creds")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;							// user_id (PK)
	
//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
//	private Student student;
//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
//	private Admin admin;
//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
//	private Professor professor;
	
	@Column(name = "user_email")
//	@NotNull
	private String userEmail;	// user_email
	
	@Column(name = "user_type")
//	@NotNull
	@Enumerated(EnumType.STRING)
	private UserType userType;	// user_type
	
	@Column(name = "reg_status")
//	@NotNull
	@Enumerated(EnumType.STRING)
	private RegistrationStatus registrationStatus;	// reg_status
	
	@Column(name = "user_password")
//	@NotNull
	private String password;

	public User() {}
	
	public User(
			Integer userId, 
//			@NotNull 
			String userEmail, 
//			@NotNull 
			UserType userType,
//			@NotNull 
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userEmail=" + userEmail + ", userType=" + userType
				+ ", registrationStatus=" + registrationStatus + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, registrationStatus, userEmail, userId, userType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(password, other.password) && registrationStatus == other.registrationStatus
				&& Objects.equals(userEmail, other.userEmail) && Objects.equals(userId, other.userId)
				&& userType == other.userType;
	}
	
	
	
}
