package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;

/**
 * 
 */
public interface CourseDAO {

	/**
	 * 
	 * @return
	 */
	public List<Course> findAll();
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public List<Course> findAllByCourseIdIn(Set<Integer> courseIds);
	
	/**
	 * 
	 * @param semester
	 * @return
	 */
	public List<Course> findAllBySemester(Integer semester);

	public List<Course> findCourseByProfessorID(Integer professorID);
}
