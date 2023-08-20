/**
 * 
 */
package com.wibmo.utils;

import com.wibmo.bean.Professor;

/**
 * 
 */
public class ProfessorNotAssignedForCourseException extends Exception {

	private Integer professorId;
	private Integer courseId;
	
	public ProfessorNotAssignedForCourseException(
			Integer professorId,
			Integer courseId) {
		this.professorId = professorId;
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Professor with Id: " + professorId + " is NOT assigned to Course Id: " + courseId + ".";
		
	}
}
