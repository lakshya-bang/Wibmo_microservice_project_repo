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

	// add admin dao method to course dao
	public void viewAllCourse();
	public boolean saveCourse(Course course);
	public boolean deleteCourse(int courseId);
	public boolean assignCoursesToProfessor(int courseId, int professorId);
	
}
