/**
 * 
 */
package com.wibmo.exception;

import com.wibmo.entity.Student;

/**
 * 
 */
public class StudentAlreadyRegisteredForCourseInSemesterException extends Exception {

	private Student student;
	private Integer courseId;
	
	public StudentAlreadyRegisteredForCourseInSemesterException(
			Student student, Integer courseId) {
		
		this.student = student;
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + student.getStudentId() + " Already Registered for Course Id: " + courseId + " in Semester: " + student.getCurrentSemester();
	}
}
