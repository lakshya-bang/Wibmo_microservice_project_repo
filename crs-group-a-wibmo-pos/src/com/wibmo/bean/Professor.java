/**
 * 
 */
package com.wibmo.bean;

/**
 * Table name = professor
 * FOREIGN KEY(professor_id) REFERENCES User(user_id)
 */
public class Professor {

	private Integer professorId;	// professor_id
	private String department;		// department
	
	public Professor() {}
	
	public Professor(Integer professorId, String department) {
		this.professorId = professorId;
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
		return "Professor [professorId=" + professorId + ", department=" + department + "]";
	}
}
