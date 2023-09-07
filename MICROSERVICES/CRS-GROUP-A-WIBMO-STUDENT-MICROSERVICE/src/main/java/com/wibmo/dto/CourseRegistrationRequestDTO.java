package com.wibmo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */
public class CourseRegistrationRequestDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer studentId;
	private Integer semester;
	private List<Integer> primaryCourseIds;
	private List<Integer> alternativeCourseIds;
	
	public CourseRegistrationRequestDTO() {}
	
	public CourseRegistrationRequestDTO(
			Integer studentId, 
			Integer semester, 
			List<Integer> primaryCourseIds,
			List<Integer> alternativeCourseIds) {
		this.studentId = studentId;
		this.semester = semester;
		this.primaryCourseIds = primaryCourseIds;
		this.alternativeCourseIds = alternativeCourseIds;
	}

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
