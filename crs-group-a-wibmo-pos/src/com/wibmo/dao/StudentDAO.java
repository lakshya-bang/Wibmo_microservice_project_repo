package com.wibmo.dao;
import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

import java.util.List;
import java.util.Optional;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;

public interface StudentDAO {
	
	public Student getStudentById(Long userId);
	
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
