package com.wibmo.business;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

public interface StudentOperation {
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */

	public boolean registerCourse(Long studentId,int courseId);
	public ReportCard getReportCard(Long studentId);
	public Optional<Student> getStudentById(Long studentId);
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public boolean registerCourses(Long studentId, Set<Long> courseIds);
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public ReportCard getReportCard(int studentId);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public boolean addCourse(int courseId);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public boolean dropCourse(int courseId);
	
	/**
	 * 
	 * @param billId
	 * @return
	 */
	public BillDueNotification payBill(int billId);
	
	/**
	 * 
	 * @return
	 */
	public List<Course> viewRegisteredCourses();
}	
