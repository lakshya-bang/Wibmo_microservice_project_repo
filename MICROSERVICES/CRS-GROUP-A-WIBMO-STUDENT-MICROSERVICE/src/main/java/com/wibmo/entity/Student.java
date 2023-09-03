/**
 * 
 */
package com.wibmo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wibmo.service.StudentServiceImpl;

/**
 * 
 */
@Entity
@Table(name = "student")
public class Student {
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

	@Id
	@Column(name = "student_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;			// student_id (PK)
	
	@Column(name = "user_id")
	private Integer userId;				// user_id (FK)
	
	@Column(name = "student_email")
	private String studentEmail;		// student_email
	
	@Column(name = "student_name")
	private String studentName;			// student_name
	
	@Column(name = "semester")
	private Integer currentSemester;	// semester

	public Student() {}
	
	public Student(
			Integer studentId, 
			Integer userId,
			String studentEmail, 
			String studentName,
			Integer currentSemester) {
		this.studentId = studentId;
		this.userId = userId;
		this.studentEmail = studentEmail;
		this.studentName = studentName;
		this.currentSemester = currentSemester;
	}

	/**
	 * @return the studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
	 * @return the studentEmail
	 */
	public String getStudentEmail() {
		return studentEmail;
	}

	/**
	 * @param studentEmail the studentEmail to set
	 */
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the currentSemester
	 */
	public Integer getCurrentSemester() {
		return currentSemester;
	}

	/**
	 * @param currentSemester the currentSemester to set
	 */
	public void setCurrentSemester(Integer currentSemester) {
		this.currentSemester = currentSemester;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", userId=" + userId + ", studentEmail=" + studentEmail
				+ ", studentName=" + studentName + ", currentSemester=" + currentSemester + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentSemester, studentEmail, studentId, studentName, userId);
	}

	@Override
	public boolean equals(Object obj) {
		logger.info("Checking equal object.");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(currentSemester, other.currentSemester)
				&& Objects.equals(studentEmail, other.studentEmail) && Objects.equals(studentId, other.studentId)
				&& Objects.equals(studentName, other.studentName) && Objects.equals(userId, other.userId);
	}
	
	
}
