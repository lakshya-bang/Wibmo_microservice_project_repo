package com.wibmo.business;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

public interface StudentOperation {

	public Student getStudentById(Long userId);

	ReportCard getReportCard(Long studentId);

	boolean registerCourses(Long studentId, Set<Long> courseIds);

	BillDueNotification payBill(int billId);

	boolean registerCourse(Long studentId, int courseId);

	ReportCard getReportCard(int studentId);

	boolean dropCourse(int courseId, Long studentId);

	boolean addCourse(int courseId, Long studentId);

	List<Course> viewRegisteredCourses(Long studentId);
	
}	
