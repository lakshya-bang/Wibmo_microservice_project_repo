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
@Table(name = "professor")
public class Professor {
	@Id
	@Column(name = "professor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer professorId;	// professor_id (FK)
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "professor_id")
    @MapsId
	private User user;
	
	@Column(name = "professor_email")
	@NotNull
	private String professorEmail;	// professor_email
	
	@Column(name = "professor_name")
	@NotNull
	private String professorName;	// professor_name
	
	@Column(name = "department")
	@NotNull
	private String department;		// department

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
	
	
}
