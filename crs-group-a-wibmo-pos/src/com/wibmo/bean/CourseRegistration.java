package com.wibmo.bean;

import com.wibmo.enums.RegistrationStatus;

/**
 * 
 * Table Name = registered_courses
 */
public class CourseRegistration {

	private Integer courseRegId;					// course_reg_id
	private Integer studentId;						// student_id
	private Integer semester;						// semester
	private Integer primaryCourse1Id;				// primary_course_1_id
	private Integer primaryCourse2Id;				// primary_course_2_id
	private Integer primaryCourse3Id;				// primary_course_3_id
	private Integer primaryCourse4Id;				// primary_course_4_id
	private Integer alternativeCourse1Id;			// alternative_course_1_id
	private Integer alternativeCourse2Id;			// alternative_course_2_id
	private RegistrationStatus registrationStatus;	// registration_status
	
	public CourseRegistration() {}
	
	public CourseRegistration(
			Integer courseRegId, 
			Integer studentId, 
			Integer semester, 
			Integer primaryCourse1Id,
			Integer primaryCourse2Id, 
			Integer primaryCourse3Id, 
			Integer primaryCourse4Id, 
			Integer alternativeCourse1Id,
			Integer alternativeCourse2Id, 
			RegistrationStatus registrationStatus) {
		this.courseRegId = courseRegId;
		this.studentId = studentId;
		this.semester = semester;
		this.primaryCourse1Id = primaryCourse1Id;
		this.primaryCourse2Id = primaryCourse2Id;
		this.primaryCourse3Id = primaryCourse3Id;
		this.primaryCourse4Id = primaryCourse4Id;
		this.alternativeCourse1Id = alternativeCourse1Id;
		this.alternativeCourse2Id = alternativeCourse2Id;
		this.registrationStatus = registrationStatus;
	}



	/**
	 * @return the courseRegId
	 */
	public Integer getCourseRegId() {
		return courseRegId;
	}
	/**
	 * @param courseRegId the courseRegId to set
	 */
	public void setCourseRegId(Integer courseRegId) {
		this.courseRegId = courseRegId;
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
	 * @return the primaryCourse1Id
	 */
	public Integer getPrimaryCourse1Id() {
		return primaryCourse1Id;
	}
	/**
	 * @param primaryCourse1Id the primaryCourse1Id to set
	 */
	public void setPrimaryCourse1Id(Integer primaryCourse1Id) {
		this.primaryCourse1Id = primaryCourse1Id;
	}
	/**
	 * @return the primaryCourse2Id
	 */
	public Integer getPrimaryCourse2Id() {
		return primaryCourse2Id;
	}
	/**
	 * @param primaryCourse2Id the primaryCourse2Id to set
	 */
	public void setPrimaryCourse2Id(Integer primaryCourse2Id) {
		this.primaryCourse2Id = primaryCourse2Id;
	}
	/**
	 * @return the primaryCourse3Id
	 */
	public Integer getPrimaryCourse3Id() {
		return primaryCourse3Id;
	}
	/**
	 * @param primaryCourse3Id the primaryCourse3Id to set
	 */
	public void setPrimaryCourse3Id(Integer primaryCourse3Id) {
		this.primaryCourse3Id = primaryCourse3Id;
	}
	/**
	 * @return the primarycourse4Id
	 */
	public Integer getPrimaryCourse4Id() {
		return primaryCourse4Id;
	}
	/**
	 * @param primarycourse4Id the primarycourse4Id to set
	 */
	public void setPrimaryCourse4Id(Integer primaryCourse4Id) {
		this.primaryCourse4Id = primaryCourse4Id;
	}
	/**
	 * @return the alternativeCourse1Id
	 */
	public Integer getAlternativeCourse1Id() {
		return alternativeCourse1Id;
	}
	/**
	 * @param alternativeCourse1Id the alternativeCourse1Id to set
	 */
	public void setAlternativeCourse1Id(Integer alternativeCourse1Id) {
		this.alternativeCourse1Id = alternativeCourse1Id;
	}
	/**
	 * @return the alternativeCourse2Id
	 */
	public Integer getAlternativeCourse2Id() {
		return alternativeCourse2Id;
	}
	/**
	 * @param alternativeCourse2Id the alternativeCourse2Id to set
	 */
	public void setAlternativeCourse2Id(Integer alternativeCourse2Id) {
		this.alternativeCourse2Id = alternativeCourse2Id;
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
	@Override
	public String toString() {
		return "CourseRegistration [courseRegId=" + courseRegId + ", studentId=" + studentId + ", semester=" + semester
				+ ", primaryCourse1Id=" + primaryCourse1Id + ", primaryCourse2Id=" + primaryCourse2Id
				+ ", primaryCourse3Id=" + primaryCourse3Id + ", primarycourse4Id=" + primaryCourse4Id
				+ ", alternativeCourse1Id=" + alternativeCourse1Id + ", alternativeCourse2Id=" + alternativeCourse2Id
				+ ", registrationStatus=" + registrationStatus + "]";
	}
	
}
