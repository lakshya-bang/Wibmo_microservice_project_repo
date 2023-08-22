package com.wibmo.exception;

import com.wibmo.bean.Student;

/**
 * 
 */
public class StudentNotRegisteredForCourseInSemesterException extends Exception {

	private Student student;
	private Integer courseId;
	
	public StudentNotRegisteredForCourseInSemesterException(Student student, Integer courseId) {
		this.student = student;
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + student.getStudentId() + " NOT Registered for Course Id: " + courseId + " in Semester: " + student.getCurrentSemester();
	}
}
