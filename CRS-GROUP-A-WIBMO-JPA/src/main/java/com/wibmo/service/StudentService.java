package com.wibmo.service;

import java.util.List;

import com.wibmo.entity.Student;

/**
 * 
 */
public interface StudentService {

	/**
	 * @param integer
	 * @return
	 */
	public Student getStudentById(Integer integer);
	
	/**
	 * 
	 * @return
	 */
	public List<Student> getAllStudents();

	/**
	 * 
	 * @param student
	 */
	public void add(Student student);

}	
