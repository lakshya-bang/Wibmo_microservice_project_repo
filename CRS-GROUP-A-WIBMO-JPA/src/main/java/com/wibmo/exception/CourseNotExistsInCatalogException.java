/**
 * 
 */
package com.wibmo.exception;

/**
 * Throws exception when Course does not exist for a given courseId.
 */
public class CourseNotExistsInCatalogException extends Exception {

	private Integer courseId;
	
	public CourseNotExistsInCatalogException(Integer courseId) {
		this.courseId = courseId;
	}
	
	@Override
	public String getMessage() {
		return "Course with Id: " + courseId + " does not exist in the Catalog.";
	}
	
}
