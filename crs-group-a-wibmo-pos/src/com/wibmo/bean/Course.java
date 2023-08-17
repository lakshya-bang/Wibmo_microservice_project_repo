package com.wibmo.bean;

/**
 * 
 * Table Name: course
 * Primary Key: (courseId) Auto Increment
 */
public class Course {
	
	private Integer courseId;
	private Integer year;
	private Integer semester;
	private String name;
	private String department;
	private Integer professorId;
	private Boolean isCancelled;
	private Integer noOfSeats;
	private String courseType;
	
	

	public Course(Integer courseId, Integer year, Integer semester, String name, String department, Integer professorId,
			Boolean isCancelled, Integer noOfSeats, String courseType) {
		this.courseId = courseId;
		this.year = year;
		this.semester = semester;
		this.name = name;
		this.department = department;
		this.professorId = professorId;
		this.isCancelled = isCancelled;
		this.noOfSeats = noOfSeats;
		this.courseType = courseType;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", year=" + year + ", semester=" + semester + ", name=" + name
				+ ", department=" + department + ", professorId=" + professorId + ", isCancelled=" + isCancelled
				+ ", noOfSeats=" + noOfSeats + "]";
	}
	
}