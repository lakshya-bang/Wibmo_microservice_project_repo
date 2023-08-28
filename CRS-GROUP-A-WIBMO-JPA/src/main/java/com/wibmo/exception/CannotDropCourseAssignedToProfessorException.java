package com.wibmo.exception;

/**
 * Throws exception when the Admin attempts to drop a course
 * when it is assigned to a Professor.
 */
public class CannotDropCourseAssignedToProfessorException extends Exception {

	private Integer courseId;
	private Integer professorId;
	
	public CannotDropCourseAssignedToProfessorException(
			Integer courseId, Integer professorId) {
		this.courseId = courseId;
		this.professorId = professorId;
	}
	
	@Override
	public String getMessage() {
		return "Cannot Drop Course! Course Id: " + courseId + " assigned to Professor Id: " + professorId + ".";
	}
}
