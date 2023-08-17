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
}
