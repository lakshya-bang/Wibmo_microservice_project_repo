/**
 * 
 */
package com.wibmo.bean;

import java.util.Objects;

/**
 * Table Name = report_card
 */
public class ReportCard {
	
	private Integer reportId;			// report_id (PK)
	private Integer studentId;			// student_id
	private Integer courseId;			// course_id
	private String grade;				// grade
	private Integer semester;			// semester
	private Integer year;				// year
	
	public ReportCard() {}
	
	public ReportCard(
			Integer reportId, 
			Integer studentId, 
			Integer courseId, 
			String grade, 
			Integer semester,
			Integer year) {
		this.reportId = reportId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.grade = grade;
		this.semester = semester;
		this.year = year;
	}

	/**
	 * @return the reportId
	 */
	public Integer getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
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

	@Override
	public String toString() {
		return "ReportCard [reportId=" + reportId + ", studentId=" + studentId + ", courseId=" + courseId + ", grade="
				+ grade + ", semester=" + semester + ", year=" + year + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseId, grade, reportId, semester, studentId, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportCard other = (ReportCard) obj;
		return Objects.equals(courseId, other.courseId) && Objects.equals(grade, other.grade)
				&& Objects.equals(reportId, other.reportId) && Objects.equals(semester, other.semester)
				&& Objects.equals(studentId, other.studentId) && Objects.equals(year, other.year);
	}
	
	
}
