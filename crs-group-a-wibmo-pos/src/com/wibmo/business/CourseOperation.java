package com.wibmo.business;

import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Course;

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
}
