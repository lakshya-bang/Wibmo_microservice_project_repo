/**
 * 
 */
package com.wibmo.dao;

import java.util.Set;
import java.util.ArrayList;
import java.util.Map;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Professor;
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

	/**
	 * 
	 * @param professor
	 * @return 
	 */
	public Map<Integer, ArrayList<Integer>> getStudentsByCourseId(Professor professor);

	public void viewCourseRegistrationStatus(RegistrationStatus regStatus);

	public boolean approveRegistrationStatus(int courseRegId);
	
	public boolean rejectRegistrationStatus(int courseRegId);

}
