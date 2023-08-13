package com.wibmo.business;

import java.util.Set;

import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

public interface CourseRegistrationOperation {

	/**
	 * 
	 * @param studentId
	 * @param courseIds
	 * @return
	 * 
	 * // Admin will approve the relevant status of the registration
	 * @throws StudentNotFoundException 
	 * @throws CoursesNotAvailableForRegistrationException 
	 * @throws StudentAlreadyRegisteredForSemesterException 
	 */
	public void register(Long studentId, Set<Long> courseIds) throws StudentNotFoundException, CoursesNotAvailableForRegistrationException, StudentAlreadyRegisteredForSemesterException;
	
	/**
	 * 
	 * @param studentId
	 * @param semOfStudy
	 * @return
	 */
	public Boolean getRegistrationStatusByStudentIdAndSemOfStudy(Long studentId, Integer semOfStudy);
	
	/**
	 * 
	 * @return
	 */
	public Set<Long> getRegisteredCourseIdsByStudentIdAndSemOfStudy(Long studentId, Integer semOfStudy);
}
