package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Student;

public interface ProfessorBusiness {
	public boolean authenticate();
	public List<Student> viewEnrolledStudents();
}
