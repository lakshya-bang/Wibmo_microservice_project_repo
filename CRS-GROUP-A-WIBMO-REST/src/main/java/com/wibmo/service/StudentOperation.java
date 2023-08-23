package com.wibmo.service;

import com.wibmo.bean.Student;

public interface StudentOperation {

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
