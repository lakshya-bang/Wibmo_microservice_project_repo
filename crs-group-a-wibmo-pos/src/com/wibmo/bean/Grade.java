/**
 * 
 */
package com.wibmo.bean;

/**
 * Table Name = grade_details
 */
public class Grade {
	
	private Integer gradeId;			// grade_detail_id (PK)
	private Integer studentId;			// student_id
	private Integer courseId;			// course_id
	private String grade;				// grade
	private Integer semester;			// semester
	private Integer year;				// year
	
	public Grade() {}
	
	
	
	public Grade(
			Integer gradeId, 
			Integer studentId, 
			Integer courseId, 
			String grade, 
			Integer semester,
			Integer year) {
		super();
		this.gradeId = gradeId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.grade = grade;
		this.semester = semester;
		this.year = year;
	}



	/**
	 * @return the gradeDetailId
	 */
	public Integer getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeDetailId the gradeDetailId to set
	 */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
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
	/**
	 * @return the semester
	 */
	public Integer getSemester() {
		return semester;
	}
	/**
	 * @param semeste the semester to set
	 */
	public void setSemester(Integer semester) {
		this.semester = semester;
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
	
	
	
}
