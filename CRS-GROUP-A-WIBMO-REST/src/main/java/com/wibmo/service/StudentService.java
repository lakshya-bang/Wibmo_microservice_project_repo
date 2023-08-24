package com.wibmo.service;

import com.wibmo.entity.Student;

public interface StudentService {

	/**
	 * @param integer
	 * @return
	 */
	public Student getStudentById(Integer integer);

	/**
	 * 
	 * @param student
	 */
	public void add(Student student);

}	
