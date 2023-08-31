package com.wibmo.exception;

import com.wibmo.bean.Student;
import com.wibmo.enums.CourseType;

public class StudentAlreadyRegisteredForAllAlternativeCoursesException extends Exception {

	public Student student;
	
	public StudentAlreadyRegisteredForAllAlternativeCoursesException(Student student) {
		this.student = student;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + student.getStudentId() + " Already Registered for all " + CourseType.ALTERNATIVE.toString() + " Courses";
	}
}
