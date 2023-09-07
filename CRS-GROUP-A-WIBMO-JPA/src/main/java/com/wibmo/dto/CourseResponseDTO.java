/**
 * 
 */
package com.wibmo.dto;

import com.wibmo.enums.CourseType;

/**
 * 
 */
public class CourseResponseDTO {
	
	private Integer courseId;
	private String courseTitle;
	private Integer semester;
	private Integer year; 
	private String department;
	private Boolean isCancelled;
	private Integer noOfSeats;
	private CourseType courseType;
	private Integer professorId;
	private String professorEmail;
	private String professorName;
	
	public CourseResponseDTO() {}
	
	public CourseResponseDTO(
			Integer courseId, 
			String courseTitle, 
			Integer semester, 
			Integer year, 
			String department,
			Boolean isCancelled, 
			Integer noOfSeats, 
			CourseType courseType, 
			Integer professorId, 
			String professorEmail,
			String professorName) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.semester = semester;
		this.year = year;
		this.department = department;
		this.isCancelled = isCancelled;
		this.noOfSeats = noOfSeats;
		this.courseType = courseType;
		this.professorId = professorId;
		this.professorEmail = professorEmail;
		this.professorName = professorName;
	}
	/**
	 * @return the courseI
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * @param courseI the courseI to set
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
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
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
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
	 * @return the isCancelled
	 */
	public Boolean getIsCancelled() {
		return isCancelled;
	}
	/**
	 * @param isCancelled the isCancelled to set
	 */
	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
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

	@Override
	public String toString() {
		return "CourseResponseDTO [courseId=" + courseId + ", courseTitle=" + courseTitle + ", semester=" + semester
				+ ", year=" + year + ", department=" + department + ", isCancelled=" + isCancelled + ", noOfSeats="
				+ noOfSeats + ", courseType=" + courseType + ", professorId=" + professorId + ", professorEmail="
				+ professorEmail + ", professorName=" + professorName + "]";
	}
	
}
