package com.wibmo.bean;

/**
 *
 */
public class Student {

	private Integer studentId;			// student_id
	private String studentName;			// student_name
	private Integer currentSemester;	// semester
	
	public Student() {}
	
	public Student(
			Integer studentId, 
			String studentName,
			Integer currentSemester) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.currentSemester = currentSemester;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", currentSemester="
				+ currentSemester + "]";
	}
	
}
