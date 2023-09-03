package com.wibmo.dto;

/**
 * 
 */
public class ReportCardResponseDTO {

	private Integer courseId;
	private String courseTitle;
	private String grade;
	
	public ReportCardResponseDTO(Integer courseId, String courseTitle, String grade) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.grade = grade;
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
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
