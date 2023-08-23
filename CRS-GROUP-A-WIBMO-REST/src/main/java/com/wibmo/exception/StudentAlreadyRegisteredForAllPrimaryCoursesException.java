package com.wibmo.exception;

import com.wibmo.bean.Student;
import com.wibmo.enums.CourseType;

public class StudentAlreadyRegisteredForAllPrimaryCoursesException extends Exception {

public Student student;
	
	public StudentAlreadyRegisteredForAllPrimaryCoursesException(Student student) {
		this.student = student;
	}
	
	@Override
	public String getMessage() {
		return "Student Id: " + student.getStudentId() + " Already Registered for all " + CourseType.PRIMARY.toString() + " Courses";
	}
}
