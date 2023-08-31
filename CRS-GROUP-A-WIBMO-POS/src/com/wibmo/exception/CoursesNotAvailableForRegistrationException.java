/**
 * 
 */
package com.wibmo.exception;

import java.util.Set;

/**
 * 
 */
public class CoursesNotAvailableForRegistrationException extends Exception {

	private Set<Long> courseIds;
	
	public CoursesNotAvailableForRegistrationException(Set<Long> courseIds) {
		this.courseIds = courseIds;
	}
	
	@Override
	public String getMessage() {
		return "Course Ids: " + courseIds + " are NOT available for Registration.";
	}
	
}
