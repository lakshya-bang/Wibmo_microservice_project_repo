package com.wibmo.dto;

import java.io.Serializable;

/**
 * 
 */
public class AddDropCourseDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer studentId;
	private Integer semester;
	private Integer courseId;
	
	/**
	 * @return the student
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * @param student the student to set
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
	
}
