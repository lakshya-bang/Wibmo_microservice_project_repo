package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.enums.CourseType;

/**
 * 
 */
public interface CourseDAO {

	/**
	 * 
	 * @param semester
	 * @return
	 */
	public List<Course> findAllBySemester(Integer semester);
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public List<Course> findAllByCourseIdIn(Set<Integer> courseIds);
	
	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public List<Course> findAllByProfessorId(Integer professorId);

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public CourseType findCourseTypeByCourseId(Integer courseId);

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Boolean existsByCourseId(Integer courseId);
	
	/**
	 * 
	 * @return
	 */
	public List<Course> findAll();
	
	/**
	 * 
	 * @param course
	 * @return
	 */
	public Boolean save(Course course);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Boolean deleteCourseById(Integer courseId);
	
	/**
	 * TODO: enhance to assigneCoursesToProfessor(Set<Integer> courseIds, Integer professorId)
	 * 
	 * @param courseId
	 * @param professorId
	 * @return
	 */
	public Boolean setProfessorIdAsWhereCourseIdIs(Integer professorId, Integer courseId);

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Integer findProfessorIdByCourseId(Integer courseId);
	
}
