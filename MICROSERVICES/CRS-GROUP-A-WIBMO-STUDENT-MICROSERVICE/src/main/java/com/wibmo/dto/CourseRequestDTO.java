package com.wibmo.dto;

import com.wibmo.enums.CourseType;

/**
 * 
 */
public class CourseRequestDTO {
 
	private String courseTitle;
	private Integer semester;
	private String department;
	private Integer professorId;
	private Integer noOfSeats;
	private CourseType courseType;
	
	public CourseRequestDTO() {}

	public CourseRequestDTO(
			String courseTitle, 
			Integer semester, 
			String department, 
			Integer professorId,
			Integer noOfSeats, 
			CourseType courseType) {
		this.courseTitle = courseTitle;
		this.semester = semester;
		this.department = department;
		this.professorId = professorId;
		this.noOfSeats = noOfSeats;
		this.courseType = courseType;
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
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
	 * @return the noOfSeats
	 */
	public Integer getNoOfSeats() {
		return noOfSeats;
	}

	/**
	 * @param noOfSeats the noOfSeats to set
	 */
	public void setNoOfSeats(Integer noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	/**
	 * @return the courseType
	 */
	public CourseType getCourseType() {
		return courseType;
	}

	/**
	 * @param courseType the courseType to set
	 */
	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

}
