package com.wibmo.entity;

import java.util.Objects;

/**
 *
 */
public class Student {

	private Integer studentId;			// student_id (FK)
	private String studentEmail;		// student_email
	private String studentName;			// student_name
	private Integer currentSemester;	// semester
	
	public Student() {}
	
	public Student(
			Integer studentId, 
			String studentEmail,
			String studentName,
			Integer currentSemester) {
		this.studentId = studentId;
		this.studentEmail = studentEmail;
		this.studentName = studentName;
		this.currentSemester = currentSemester;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentEmail() {
		return studentEmail;
	}
	
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(Integer currentSemester) {
		this.currentSemester = currentSemester;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentSemester, studentEmail, studentId, studentName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(currentSemester, other.currentSemester)
				&& Objects.equals(studentEmail, other.studentEmail) 
				&& Objects.equals(studentId, other.studentId)
				&& Objects.equals(studentName, other.studentName);
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentEmail=" + studentEmail + ", studentName=" + studentName
				+ ", currentSemester=" + currentSemester + "]";
	}
	
}
