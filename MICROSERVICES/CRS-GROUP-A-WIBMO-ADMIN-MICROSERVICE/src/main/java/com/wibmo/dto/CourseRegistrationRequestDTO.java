/**
 * 
 */
package com.wibmo.dto;

import java.util.List;

import com.wibmo.entity.Student;

/**
 * 
 */
public class CourseRegistrationRequestDTO {
	
	private Integer studentId;
	private Integer semester;
	private List<Integer> primaryCourseIds;
	private List<Integer> alternativeCourseIds;
	
	public CourseRegistrationRequestDTO() {}
	
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

	@Override
	public String toString() {
		return "CourseRegistrationRequestDTO [studentId=" + studentId + ", semester=" + semester + ", primaryCourseIds=" + primaryCourseIds
				+ ", alternativeCourseIds=" + alternativeCourseIds + "]";
	}
	
}
