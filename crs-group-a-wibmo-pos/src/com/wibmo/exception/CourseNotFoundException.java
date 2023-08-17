/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class CourseNotFoundException extends Exception {

	private Integer courseId;
	
	public CourseNotFoundException(Integer courseId) {
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Course with Id: " + courseId + " Does Not Exists.";
	}
	
}
