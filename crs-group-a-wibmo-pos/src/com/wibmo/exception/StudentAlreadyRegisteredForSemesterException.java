/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class StudentAlreadyRegisteredForSemesterException extends Exception {

	private Long studentId;
	private Integer semOfStudy;
	
	public StudentAlreadyRegisteredForSemesterException(Long studentId, Integer semOfStudy) {
		this.studentId = studentId;
		this.semOfStudy = semOfStudy;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " already registered for Sem: " + semOfStudy;
	}
}
