/**
 * 
 */
package com.wibmo.dao;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Student;

/**
 * 
 */
public interface CourseRegistrationDAO {
	
	/**
	 * 
	 * @param courseRegistration
	 */
	public void save(CourseRegistration courseRegistration);
	
	/**
	 * 
	 * @param student
	 * @return
	 */
	public CourseRegistration findByStudent(Student student);

	/**
	 * 
	 * @param student
	 * @return
	 */
	public String findRegistrationStatusByStudent(Student student);
	
	/**
	 * 
	 * @param student
	 * @return
	 */
	public Boolean existsByStudent(Student student);
}
