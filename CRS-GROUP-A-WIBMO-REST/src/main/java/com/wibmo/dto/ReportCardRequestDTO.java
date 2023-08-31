package com.wibmo.dto;

/**
 * 
 */
public class ReportCardRequestDTO {

	private Integer courseId;
	private Integer studentId;
	private String grade;
	
	public ReportCardRequestDTO() {}
	
	public ReportCardRequestDTO(
			Integer courseId, 
			Integer studentId, 
			String grade) {
		this.courseId = courseId;
		this.studentId = studentId;
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
