package com.wibmo.business;

import java.util.List;
import java.util.Optional;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

public interface StudentOperation {
	public boolean authenticate() ;
	public boolean login(Long userId,String password);
	public boolean registerCourse(Long studentId,int courseId);
	public ReportCard getReportCard(Long studentId);
	public boolean addCourse(int courseId);
	public boolean dropCourse(int courseId);
	public BillDueNotification payBill(int billId);
	public List<Course> viewRegisteredCourses();
	public Optional<Student> getStudentById(Long studentId);
}	
