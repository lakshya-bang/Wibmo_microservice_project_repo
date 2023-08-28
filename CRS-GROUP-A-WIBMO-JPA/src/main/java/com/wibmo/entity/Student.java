/**
 * 
 */
package com.wibmo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "student")
public class Student {
	@Id
	@Column(name = "student_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;			// student_id (FK)
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "student_id")
    @MapsId
	private User user;
	
	@Column(name = "student_email")
	@NotNull
	private String studentEmail;		// student_email
	
	@Column(name = "student_name")
	@NotNull
	private String studentName;			// student_name
	
	@Column(name = "semester")
	@NotNull
	private Integer currentSemester;	// semester

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
	
	
	
}
