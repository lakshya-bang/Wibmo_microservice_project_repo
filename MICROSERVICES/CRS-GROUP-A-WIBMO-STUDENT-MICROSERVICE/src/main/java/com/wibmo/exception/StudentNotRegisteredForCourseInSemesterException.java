package com.wibmo.exception;

/**
 * 
 */
public class StudentNotRegisteredForCourseInSemesterException extends Exception {

	private Integer studentId;
	private Integer semester;
	private Integer courseId;
	
	public StudentNotRegisteredForCourseInSemesterException(
			Integer studentId, Integer semester, Integer courseId) {
		this.studentId = studentId;
		this.semester = semester;
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " NOT Registered for Course Id: " + courseId + " in Semester: " + semester + ".";
	}
}
