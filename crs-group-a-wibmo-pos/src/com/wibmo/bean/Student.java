package com.wibmo.bean;

/**
 * Table name = student
 * FOREIGN KEY(student_idd) REFERENCES User(user_id)
 */
public class Student {

	private Integer studentId;
	private Integer currentSemester;
	
	public Student(Integer studentId, Integer currentSemester) {
		this.studentId = studentId;
		this.currentSemester = currentSemester;
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
	 * @return the currentSemester
	 */
	public Integer getCurrentSemester() {
		return currentSemester;
	}
	/**
	 * @param currentSemester the currentSemester to set
	 */
	public void setCurrentSemester(Integer currentSemester) {
		this.currentSemester = currentSemester;
	}
	
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", currentSemester=" + currentSemester + "]";
	}

	
}
