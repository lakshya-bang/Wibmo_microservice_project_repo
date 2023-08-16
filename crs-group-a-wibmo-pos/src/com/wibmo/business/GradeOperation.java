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
	 * @param courseId
	 * @param studentIdToAssignedGradesMap
	 */
	public void uploadGrades(Integer courseId, Map<Integer, String> studentIdToAssignedGradesMap);
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Map<Integer, Grade> getSemesterToGradeMapByStudentId(Integer studentId);
}
