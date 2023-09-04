package com.wibmo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "professor")
public class Professor {
	
	@Id
	@Column(name = "professor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer professorId;	// professor_id (PK)
	
	@Column(name = "user_id")
	private Integer userId;			// user_id (FK)
	
	@Column(name = "professor_email")
	private String professorEmail;	// professor_email
	
	@Column(name = "professor_name")
	private String professorName;	// professor_name
	
	@Column(name = "department")
	private String department;		// department

	public Professor() {}
	
	public Professor(
			Integer professorId, 
			Integer userId,
			String professorEmail,
			String professorName,
			String department) {
		this.professorId = professorId;
		this.userId = userId;
		this.professorEmail = professorEmail;
		this.professorName = professorName;
		this.department = department;
	}

	/**
	 * @return the professorId
	 */
	public Integer getProfessorId() {
		return professorId;
	}

	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
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
	 * @return the professorEmail
	 */
	public String getProfessorEmail() {
		return professorEmail;
	}

	/**
	 * @param professorEmail the professorEmail to set
	 */
	public void setProfessorEmail(String professorEmail) {
		this.professorEmail = professorEmail;
	}

	/**
	 * @return the professorName
	 */
	public String getProfessorName() {
		return professorName;
	}

	/**
	 * @param professorName the professorName to set
	 */
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
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

	@Override
	public String toString() {
		return "Professor [professorId=" + professorId + ", userId=" + userId + ", professorEmail=" + professorEmail
				+ ", professorName=" + professorName + ", department=" + department + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, professorEmail, professorId, professorName, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		return Objects.equals(department, other.department) && Objects.equals(professorEmail, other.professorEmail)
				&& Objects.equals(professorId, other.professorId) && Objects.equals(professorName, other.professorName)
				&& Objects.equals(userId, other.userId);
	}

}
