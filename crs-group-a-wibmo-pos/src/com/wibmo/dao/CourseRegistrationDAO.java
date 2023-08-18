/**
 * 
 */
package com.wibmo.dao;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
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
	 * @param student
	 * @param courseId
	 * @return
	 */
	Boolean existsByStudentAndCourseId(Student student, Integer courseId);
	
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

	/**
	 * 
	 * @param student
	 * @return
	 */
	public Integer findCourseRegistrationIdByStudent(Student student);

	/**
	 * 
	 * @param courseRegistrationId
	 * @param courseId
	 * @return
	 */
	public Integer findAlternativeCourseIdIndexByCourseRegistrationIdForCourse(Integer courseRegistrationId, Integer courseId);
	
	/**
	 * 
	 * @param courseRegistrationId
	 * @param courseId
	 * @return
	 */
	public Integer findPrimaryCourseIdIndexByCourseRegistrationIdForCourse(Integer courseRegistrationId, Integer courseId);
	
	/**
	 * 
	 * @param index
	 * @param courseRegistrationId
	 */
	public void setAlternativeCourseIdAsNullAtIndexByCourseRegistrationId(Integer index, Integer courseRegistrationId);

	/**
	 * 
	 * @param index
	 * @param courseRegistrationId
	 */
	public void setPrimaryCourseIdAsNullAtIndexByCourseRegistrationId(Integer index, Integer courseRegistrationId);

	/**
	 * 
	 * @param courseRegistrationId
	 * @return
	 */
	public Integer findFirstVacantAlternativeCourseIdIndexByCourseRegistrationId(Integer courseRegistrationId);
	
	/**
	 * 
	 * @param courseRegistrationId
	 * @return
	 */
	public Integer findFirstVacantPrimaryCourseIdIndexByCourseRegistrationId(Integer courseRegistrationId);

	/**
	 * 
	 * @param courseId
	 * @param alternativeCourseIdIndex
	 * @param courseRegistrationId
	 */
	public void setAlternativeCourseIdAsAtIndexByCourseRegistrationId(
			Integer courseId,
			Integer alternativeCourseIdIndex, 
			Integer courseRegistrationId);

	/**
	 * 
	 * @param courseId
	 * @param primaryCourseIdIndex
	 * @param courseRegistrationId
	 */
	public void setPrimaryCourseIdAsAtIndexByCourseRegistrationId(
			Integer courseId,
			Integer primaryCourseIdIndex, 
			Integer courseRegistrationId);
	
	public void viewCourseRegistrationStatus(RegistrationStatus regStatus);

	public boolean approveRegistrationStatus(int courseRegId);
	
	public boolean rejectRegistrationStatus(int courseRegId);

}
