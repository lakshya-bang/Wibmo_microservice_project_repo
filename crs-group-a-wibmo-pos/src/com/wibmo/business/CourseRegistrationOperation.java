package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
public interface CourseRegistrationOperation {

	/**
	 * 
	 * @param primaryCourses
	 * @param alternativeCourses
	 * @param student
	 */
	public void register(
			List<Integer> primaryCourses, 
			List<Integer> alternativeCourses, 
			Student student);
	
	/**
	 * 
	 * @param studentId
	 * @param semOfStudy
	 */
	public void viewRegisteredCoursesByStudent(Student student);
	
	/**
	 * 
	 * @param student
	 * @return
	 */
	public RegistrationStatus getRegistrationStatusByStudent(Student student);
	
	/**
	 * 
	 * @param courseId
	 * @param student
	 */
	public void addCourse(Integer courseId, Student student);
	
	/**
	 * 
	 * @param courseId
	 * @param student
	 */
	public void dropCourse(Integer courseId, Student student);
}
