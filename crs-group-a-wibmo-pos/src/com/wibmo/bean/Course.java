package com.wibmo.bean;

import com.wibmo.enums.CourseType;

/**
 * 
 */
public class Course {
	
	private Integer courseId;		// course_id (PK)
	private String courseTitle;		// course_title
	private Integer semester;		// semester
	private Integer year;			// year
	private String department;		// department
	private Integer professorId;	// professor_id
	private Boolean isCancelled;	// is_cancelled
	private Integer noOfSeats;		// no_of_seats
	private CourseType courseType;	// course_type
	
	public Course() {}
	
	public Course(
			Integer courseId, 
			String courseTitle, 
			Integer semester, 
			Integer year, 
			String department,
			Integer professorId, 
			Boolean isCancelled, 
			Integer noOfSeats,
			CourseType courseType) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.semester = semester;
		this.year = year;
		this.department = department;
		this.professorId = professorId;
		this.isCancelled = isCancelled;
		this.noOfSeats = noOfSeats;
		this.courseType = courseType;
	}

	/**
	 * @return the courseId
	 */
	public Integer getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
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

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseTitle=" + courseTitle + ", semester=" + semester + ", year="
				+ year + ", department=" + department + ", professorId=" + professorId + ", isCancelled=" + isCancelled
				+ ", noOfSeats=" + noOfSeats + ", courseType=" + courseType + "]";
	}
		
	
}