/**
 * 
 */
package com.wibmo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

/**
 * 
 */
@Entity
@Table(name = "report_card")
public class ReportCard {
	
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reportId;			// report_id (PK)
    
    @Column(name = "student_id")
	private Integer studentId;			// student_id
    
    @Column(name = "course_id")
	private Integer courseId;			// course_id
    
    @Column(name = "grade")
	private String grade;				// grade
    
    @Column(name = "semester")
	private Integer semester;			// semester
    
    @Column(name = "year")
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
