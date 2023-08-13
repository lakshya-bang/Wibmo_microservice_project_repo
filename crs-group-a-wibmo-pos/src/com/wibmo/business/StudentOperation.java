package com.wibmo.business;

import java.util.List;
import java.util.Optional;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

public interface StudentOperation {
	public boolean authenticate() ;
	public boolean login();
	public boolean registerCourse(int courseId);
	public ReportCard getReportCard(int studentId);
	public boolean addCourse(int courseId);
	public boolean dropCourse(int courseId);
	public BillDueNotification payBill(int billId);
	public List<Course> viewRegisteredCourses();
	public Optional<Student> getStudentById(Long studentId);
}	
