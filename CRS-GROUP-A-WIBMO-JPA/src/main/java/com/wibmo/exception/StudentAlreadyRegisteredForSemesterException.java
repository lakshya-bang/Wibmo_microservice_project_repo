/**
 * 
 */
package com.wibmo.exception;

import com.wibmo.entity.Student;

/**
 * 
 */
public class StudentAlreadyRegisteredForSemesterException extends Exception {
	
	private Student student;
	
	public StudentAlreadyRegisteredForSemesterException(Student student) {
		this.student = student;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + student.getStudentId() + " already registered for Sem: " + student.getCurrentSemester();
	}
}
