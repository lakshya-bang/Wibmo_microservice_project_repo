/**
 * 
 */
package com.wibmo.dao;

import java.util.Set;

/**
 * 
 */
public interface CourseRegistrationDAO {

	/**
	 * Adds the given triplet into the course_registration table
	 * 
	 * @param studentId
	 * @param semOfStudy
	 * @param courseIds
	 * @return the number of rows affected.
	 */
	public Integer save(Long studentId, Integer semOfStudy, Set<Long> courseIds);
	
	public Boolean existsByStudentIdAndSemOfStudy(Long studentId, Integer semOfStudy);
	
	public Set<Long> findCourseIdsByStudentIdAndSemOfStudy(Long studentId, Integer semOfStudy);
}
