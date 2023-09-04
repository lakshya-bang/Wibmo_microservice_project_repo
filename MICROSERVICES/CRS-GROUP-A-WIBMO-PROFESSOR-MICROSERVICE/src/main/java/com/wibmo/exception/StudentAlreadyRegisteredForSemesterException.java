/**
 * 
 */
package com.wibmo.exception;

import com.wibmo.entity.Student;

/**
 * 
 */
public class StudentAlreadyRegisteredForSemesterException extends Exception {
	
	private Integer studentId;
	private Integer semester;
	
	public StudentAlreadyRegisteredForSemesterException(Integer studentId, Integer semester) {
		this.studentId = studentId;
		this.semester = semester;
	}

	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " already registered for Sem: " + semester + ".";
	}
}
