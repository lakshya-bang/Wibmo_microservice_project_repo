/**
 * 
 */
package com.wibmo.dto;

import java.io.Serializable;

/**
 * 
 */
public class CourseIdProfessorIdDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer professorId;
	private Integer courseId;
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
