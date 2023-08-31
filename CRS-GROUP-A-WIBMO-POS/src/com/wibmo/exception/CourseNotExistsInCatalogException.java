/**
 * 
 */
package com.wibmo.exception;

/**
 * 
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
