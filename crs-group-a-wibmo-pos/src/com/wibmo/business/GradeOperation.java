package com.wibmo.business;

import java.util.Map;

import com.wibmo.bean.Grade;
import com.wibmo.bean.Student;

/**
 * 
 */
public interface GradeOperation {

	/**
	 * 
	 * @param student
	 */
	public void viewGradesByStudent(Student student);
	
	/**
	 * 
	 * @param grade
	 */
	public void uploadGrade(Grade grade);
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Map<Integer, Grade> getSemesterToGradeMapByStudentId(Integer studentId);
}
