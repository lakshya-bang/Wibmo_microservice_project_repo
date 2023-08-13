/**
 * 
 */
package com.wibmo.bean;

import java.util.List;

/**
 * get's the courseList
 */
public class Catalog {
	List<Course> CourseList;

	/**
	 * @return the courseList
	 */
	public List<Course> getCourseList() {
		return CourseList;
	}

	/**
	 * @param courseList the courseList to set
	 */
	public void setCourseList(List<Course> courseList) {
		CourseList = courseList;
	}
}
