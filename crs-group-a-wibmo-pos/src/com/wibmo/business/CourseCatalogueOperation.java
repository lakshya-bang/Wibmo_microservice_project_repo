package com.wibmo.business;

import java.util.Map;
import java.util.Set;

/**
 * 
 */
public interface CourseCatalogueOperation {

	/**
	 * 
	 * 
	 * TODO: Should apply CourseProfessorCourseCatalogueProjection here
	 */
	public void viewCourseCatalogue();
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public Map<Long, Integer> getCourseIdToVacantSeatsMapping(Set<Long> courseIds);
	
	
	/**
	 * 
	 * @param courseId
	 * @param additionalSeats
	 */
	public void increaseSeatsBy(Long courseId, Integer extraSeats);
	
	/**
	 * 
	 * @param courseId
	 * @param extraSeats
	 */
	public void decreaseSeatsBy(Long courseId, Integer extraSeats);
	
	/**
	 * 
	 * @param courseId
	 */
	public void dropCourse(Long courseId);
	
	/**
	 * 
	 * @param courseId
	 * @param professorId
	 * @param availableSeats
	 */
	public void addCourse(Long courseId, Long professorId, Integer availableSeats);
	
	/**
	 * Assigns new Professor to the given Course
	 * @param courseId
	 * @param professorId
	 */
	public void updateAssignedProfessor(Long courseId, Long professorId);
}
