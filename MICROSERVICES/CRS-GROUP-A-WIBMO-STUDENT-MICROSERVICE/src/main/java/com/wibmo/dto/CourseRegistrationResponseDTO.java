package com.wibmo.dto;

import java.io.Serializable;
import java.util.List;

import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
public class CourseRegistrationResponseDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer courseRegistrationId;
	// TODO: Change to Student
	private Integer studentId;
	private Integer semester;
	private List<CourseResponseDTO> primaryCourses;
	private List<CourseResponseDTO> alternativeCourses;
	private RegistrationStatus registrationStatus;
	
	public CourseRegistrationResponseDTO() {}
	
	public CourseRegistrationResponseDTO(
			Integer courseRegistrationId,
			Integer studentId,
			Integer semester,
			List<CourseResponseDTO> primaryCourses,
			List<CourseResponseDTO> alternativeCourses,
			RegistrationStatus registrationStatus) {
		this.courseRegistrationId = courseRegistrationId;
		this.studentId = studentId;
		this.semester = semester;
		this.primaryCourses = primaryCourses;
		this.alternativeCourses = alternativeCourses;
		this.registrationStatus = registrationStatus;
	}
	
	/**
	 * @return the courseRegistrationId
	 */
	public Integer getCourseRegistrationId() {
		return courseRegistrationId;
	}
	/**
	 * @param courseRegistrationId the courseRegistrationId to set
	 */
	public void setCourseRegistrationId(Integer courseRegistrationId) {
		this.courseRegistrationId = courseRegistrationId;
	}
	/**
	 * @return the studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
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
	 * @return the primaryCourses
	 */
	public List<CourseResponseDTO> getPrimaryCourses() {
		return primaryCourses;
	}
	/**
	 * @param primaryCourses the primaryCourses to set
	 */
	public void setPrimaryCourses(List<CourseResponseDTO> primaryCourses) {
		this.primaryCourses = primaryCourses;
	}
	/**
	 * @return the alternativeCourses
	 */
	public List<CourseResponseDTO> getAlternativeCourses() {
		return alternativeCourses;
	}
	/**
	 * @param alternativeCourses the alternativeCourses to set
	 */
	public void setAlternativeCourses(List<CourseResponseDTO> alternativeCourses) {
		this.alternativeCourses = alternativeCourses;
	}
	/**
	 * @return the registrationStatus
	 */
	public RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}
	/**
	 * @param registrationStatus the registrationStatus to set
	 */
	public void setRegistrationStatus(RegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	
}
