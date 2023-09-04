package com.wibmo.exception;

import com.wibmo.enums.CourseType;

/**
 * 
 */
public class StudentAlreadyRegisteredForAllCoursesOfTypeException extends Exception {

	private Integer studentId;
	private CourseType courseType;

	public StudentAlreadyRegisteredForAllCoursesOfTypeException(Integer studentId, CourseType courseType) {
		this.studentId = studentId;
		this.courseType = courseType;
	}

	@Override
	public String getMessage() {
		return "Student Id: " + studentId + " Already Registered for all " + courseType.toString() + " Courses";
	}
}
