package com.wibmo.business;

import java.util.ArrayList;
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
	public Map<Integer, ArrayList<Grade>> getSemesterToGradeMapByStudentId(Integer studentId);
}
