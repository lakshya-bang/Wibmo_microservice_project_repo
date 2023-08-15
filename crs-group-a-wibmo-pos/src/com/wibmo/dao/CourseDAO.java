package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.CourseCatalogue;

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
}
