package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;

public interface StudentDAO {
	
	/**
	 * 
	 */
	public ReportCard getReportCard(int StudentId);
	
	/**
	 * 
	 */
	public boolean addCourse(int courseId, int StudentId);
	
	/**
	 * 
	 */
	public boolean dropCourse(int courseId, int StudentId);
	
	/**
	 * 
	 */
	public boolean payBill(float billId);
	
	/**
	 * 
	 */
	public List<Course> viewRegisteredCourses(int studentId);
}
