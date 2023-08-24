/**
 * 
 */
package com.wibmo.dto;

import java.util.List;

import com.wibmo.entity.Student;

/**
 * 
 */
public class CourseRegistrationDTO {
	
	private Student student;
	private List<Integer> primaryCourseIds;
	private List<Integer> alternativeCourseIds;
	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	/**
	 * @return the primaryCourseIds
	 */
	public List<Integer> getPrimaryCourseIds() {
		return primaryCourseIds;
	}
	/**
	 * @param primaryCourseIds the primaryCourseIds to set
	 */
	public void setPrimaryCourseIds(List<Integer> primaryCourseIds) {
		this.primaryCourseIds = primaryCourseIds;
	}
	/**
	 * @return the alternativeCourseIds
	 */
	public List<Integer> getAlternativeCourseIds() {
		return alternativeCourseIds;
	}
	/**
	 * @param alternativeCourseIds the alternativeCourseIds to set
	 */
	public void setAlternativeCourseIds(List<Integer> alternativeCourseIds) {
		this.alternativeCourseIds = alternativeCourseIds;
	}
	
}
