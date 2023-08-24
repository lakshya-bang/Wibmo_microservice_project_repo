package com.wibmo.exception;

public class ProfessorNotAssignedForCourseException extends Exception {
	
	private Integer professorId,courseId;
	
	public ProfessorNotAssignedForCourseException(Integer professorId,Integer courseId) {
		this.professorId=professorId;
		this.courseId=courseId;
	}
	
	public String getMessage() {
		return "Professor with Id: " + professorId + " is not assigned to course with Id : "+ courseId;
	}
}
