/**
 * 
 */
package com.wibmo.exception;

import com.wibmo.bean.Student;

/**
 * 
 */
public class StudentNotRegisteredForSemesterException extends Exception {

	private Student student;
	
	public StudentNotRegisteredForSemesterException(Student student) {
		this.student = student;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + student.getStudentId() + " NOT Registered for Semester: " + student.getCurrentSemester();
	}
}
