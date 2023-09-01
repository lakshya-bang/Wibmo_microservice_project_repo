/**
 * 
 */
package com.wibmo.exception;

import com.wibmo.entity.Student;

/**
 * 
 */
public class StudentAlreadyRegisteredForCourseInSemesterException extends Exception {

	private Integer studentId;
	private Integer semester;
	private Integer courseId;
	
	public StudentAlreadyRegisteredForCourseInSemesterException(
			Integer studentId, Integer semester, Integer courseId) {
		this.studentId = studentId;
		this.semester = semester;
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " Already Registered for Course Id: " + courseId + " in Semester: " + semester;
	}
}
