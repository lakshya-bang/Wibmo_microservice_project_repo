package com.wibmo.dao;

import com.wibmo.bean.Student;

/**
 * 
 */
public interface StudentDAO {

	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Student findById(Integer studentId);

	/**
	 * 
	 * @param student
	 */
	public void save(Student student);
}
