package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;

public interface StudentBusiness {
	public boolean authenticate() ;
	public boolean login();
	public boolean registerCourse(int courseId);
	public ReportCard getReportCard(int studentId);
	public boolean addCourse(int courseId);
	public boolean dropCourse(int courseId);
	public billDueNotification payBill(int billId);
	public List<Course> viewRegisteredCourses();
}	
