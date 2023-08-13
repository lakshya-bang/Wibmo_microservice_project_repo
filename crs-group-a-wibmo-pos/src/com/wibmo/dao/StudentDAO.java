package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;

public interface StudentDAO {
	
	
	/**
	 * 
	 */
//	public boolean Authenticate(int StudentID);
	/**
	 * 
	 */
	public boolean logIn(Long StudentId, String Password);
	/**
	 *
	 */
	public boolean registerCourse(Long StudentId, List<Integer> CourseIds);
	
	/**
	 * 
	 */
	public void getGrade(Long studentId);
	
	/**
	 * 
	 */
//	public void getReportCard(int StudentId);
	
	/**
	 * 
	 */
	public boolean addCourse(int courseId, Long StudentId);
	
	/**
	 * 
	 */
	public boolean dropCourse(int courseId, Long StudentId);
	
	/**
	 * 
	 */
//	public boolean payBill(float billId);
	
	/**
	 * 
	 */
	public void viewRegisteredCourses(Long StudentId);
}
