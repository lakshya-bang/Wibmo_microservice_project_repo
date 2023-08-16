/**
 * 
 */
package com.wibmo.bean;

/**
 * 
 */
public class Professor {

	private Integer professorId;	// professor_id
	private String professorName;	// professor_name
	private String department;		// department

	public Professor() {}
	
	public Professor(
			Integer professorId, 
			String professorName,
			String department) {
		this.professorId = professorId;
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
		return "Professor [professorId=" + professorId + ", professorName=" + professorName + ", department=" + department + "]";
	}
	
}
