package com.wibmo.dao;

import java.util.List;
import java.util.Map;


public interface StudentDAO {
	
	
	/**
	 * 
	 */
//	public boolean Authenticate(int StudentID);

	/**
	  * Students view their grades
	  * @param StudentId
	  */
	public boolean registerCourse(Long StudentId, List<Integer> CourseIds);
	
	/**
	  * Students view their grades
	  * @param StudentId
	  */
//	public void getGrade(Long studentId);
	public Map<Integer, String> getGrade(Long studentId);
	/**
	 * 
	 */
//	public void getReportCard(int StudentId);
	
	/**
	 * students already registered for a course can add more course
	 * @param courseId
	 * @param StudentId
	 */
	public boolean addCourse(int courseId, Long StudentId);
	
	/**
	 * Students already registered in courses can drop a course
	 * @param courseId
	 * @param StudentId
	 */
	public boolean dropCourse(int courseId, Long StudentId);
	
	/**
	 * 
	 */
//	public boolean payBill(float billId);
	
	/**
	 * Students can view their registered courses
	 * @param StudentId
	 */
	public void viewRegisteredCourses(Long StudentId);
}
