/**
 * 
 */
package com.wibmo.dao;

import java.util.Set;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;

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
	public RegistrationStatus findRegistrationStatusByStudent(Student student);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Set<Integer> findAllStudentIdsByCourseId(Integer courseId);
	
	/**
	 * 
	 * @param student
	 * @return
	 */
	public Boolean existsByStudent(Student student);
	
	/**
	 * 
	 * @param courseId
	 * @param semester
	 * @param year
	 * @return
	 */
	public CourseRegistration findByCourseIdAndSemesterAndYear(
			Integer courseId,
			Integer semester,
			Integer year);
}
