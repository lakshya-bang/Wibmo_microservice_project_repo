package com.wibmo.business;

import java.util.ArrayList;
import java.util.Map;

import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

/**
 * 
 */
public interface ReportCardOperation {

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
	public void uploadGrades(ReportCard reportCard);
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Map<Integer, ArrayList<ReportCard>> getSemesterToReportCardMapByStudentId(Integer studentId);
}
