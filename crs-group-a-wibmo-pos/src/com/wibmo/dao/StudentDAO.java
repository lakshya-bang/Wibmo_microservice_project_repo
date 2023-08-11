package com.wibmo.dao;

public interface StudentDAO {
	/**
	 * 
	 */
	public boolean Authenticate(int StudentID);
	/**
	 * 
	 */
	public boolean logIn(int StudentId, String Password);
	
	/**
	 *
	 */
	public boolean RegisterCourse(int courseId, int StudentId);
	
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
	public Course viewRegisteredCourse(int studentId);
}
