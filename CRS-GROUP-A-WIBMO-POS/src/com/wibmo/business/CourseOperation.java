package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;

public interface CourseOperation {

	/**
	 * 
	 * @param currentSemester
	 */
	public void viewCourseDetailsBySemester(Integer currentSemester);
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public Map<Integer, Course> getCourseIdToCourseMap(Set<Integer> courseIds);
	
	/**
	 * 
	 * @param professor
	 * @return
	 * @throws UserNotFoundException 
	 */
	public List<Course> getCoursesAssignedToProfessor(Integer professorId) 
			throws UserNotFoundException;
	
	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws CourseNotExistsInCatalogException 
	 */
	public CourseType getCourseTypeByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException;

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Boolean isCourseExistsInCatalog(Integer courseId);

	/**
	 * 
	 * @return
	 */
	public List<Course> getAllCourses();
	
	/**
	 * 
	 * @param course
	 * @return
	 */
	public Boolean add(Course course);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws CourseNotExistsInCatalogException 
	 * @throws CannotDropCourseAssignedToProfessorException 
	 */
	public Boolean removeCourseById(Integer courseId) 
			throws 
				CourseNotExistsInCatalogException, 
				CannotDropCourseAssignedToProfessorException;
	
	/**
	 * 
	 * @param courseId
	 * @param professorId
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 */
	public void assignCourseToProfessor(Integer courseId, Integer professorId) 
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException;

	/**
	 * 
	 * @param professor
	 */
	public void viewCoursesTaughtByProfessor(Professor professor);

	/**
	 * 
	 * @param professor
	 * @param courseId
	 * @return
	 * @throws ProfessorNotExistsInSystemException 
	 * @throws CourseNotExistsInCatalogException 
	 */
	public Boolean isProfessorAssignedForCourse(Integer professorId, Integer courseId)
		throws 
			UserNotFoundException, 
			CourseNotExistsInCatalogException;

}
