package com.wibmo.exception;

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
