package com.wibmo.exception;

/**
 * 
 */
public class CourseNotAvailableDueToSeatsFullException extends Exception {

	private Integer courseId;
	
	public CourseNotAvailableDueToSeatsFullException(Integer courseId) {
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Course Id: " + courseId + " NOT Available for Registration due to Seats Full.";
	}
}
