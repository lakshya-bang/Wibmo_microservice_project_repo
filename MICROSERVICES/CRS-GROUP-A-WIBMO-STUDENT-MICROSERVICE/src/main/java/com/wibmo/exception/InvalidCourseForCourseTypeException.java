package com.wibmo.exception;

import com.wibmo.enums.CourseType;

/**
 * 
 */
public class InvalidCourseForCourseTypeException extends Exception {

	private Integer courseId;
	private CourseType courseType;
	
	public InvalidCourseForCourseTypeException(
			Integer courseId, CourseType courseType) {
		this.courseId = courseId;
		this.courseType = courseType;
	}
	
	@Override
	public String getMessage() {
		return "Course Id: " + courseId + " is NOT a " + courseType.toString() + " Course.";
	}
}
