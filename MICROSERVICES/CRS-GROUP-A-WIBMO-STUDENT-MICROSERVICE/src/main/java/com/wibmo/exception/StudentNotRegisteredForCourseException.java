package com.wibmo.exception;

public class StudentNotRegisteredForCourseException extends Exception {

	private Integer studentId;
	private Integer courseId;
	
	public StudentNotRegisteredForCourseException(Integer studentId, Integer courseId) {
		this.studentId = studentId;
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Student with Id: " + studentId + " NOT Registered for Course with Id: " + courseId + ".";
	}
	
}
