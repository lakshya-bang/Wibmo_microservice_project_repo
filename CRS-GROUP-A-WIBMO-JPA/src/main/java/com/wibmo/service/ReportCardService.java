package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;

/**
 * 
 */
public interface ReportCardService {

	/**
	 * 
	 * @param student
	 * 
	 * TODO: Change to viewReportCardForAllSemestersByStudent()
	 */
	public void viewReportCardByStudent(Student student);
	
	/**
	 * Uploads the given list of grade cards into the Database.
	 * 
	 * <b>Note</b>: This is a general method for both save and update functionality.
	 * 
	 * @param courseId
	 * @param studentIdToAssignedGradesMap
	 */
	public void uploadReportCards(List<ReportCard> reportCards);
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Map<Integer, ArrayList<ReportCard>> getSemesterToReportCardMapByStudentId(Integer studentId);
	/**
	 * 
	 * @param student
	 * @param courseId
	 * @return
	 */
	public ReportCard getReportCardByStudentForCourse(Student student, Integer courseId);

}
