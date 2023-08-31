/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;
import com.wibmo.dao.ReportCardDAO;
import com.wibmo.dao.ReportCardDAOImpl;
import com.wibmo.exception.CourseNotExistsInCatalogException;

/**
 * 
 */
public class ReportCardOperationImpl implements ReportCardOperation {

	private final CourseOperation courseOperation;
	
	private final ReportCardDAO reportCardDAO;
	
	public ReportCardOperationImpl(
			StudentOperation studentOperation,
			CourseOperation courseOperation,
			CourseRegistrationOperation courseRegistrationOperation) {
		this.courseOperation = courseOperation;
		reportCardDAO = ReportCardDAOImpl.getInstance();
	}
	
	@Override
	public void viewReportCardByStudent(Student student) {
		
		// TODO: Map should be a TreeMap
		Map<Integer, ArrayList<ReportCard>> semesterToReportCardMap = getSemesterToReportCardMapByStudentId(student.getStudentId());
		
		Set<Integer> courseIds = new HashSet<>();
		
		semesterToReportCardMap
			.entrySet()
			.stream()
			.map(entry -> entry.getValue())
			.forEach(semWiseReportCards -> {
				semWiseReportCards
					.forEach(reportCard -> {
						courseIds.add(reportCard.getCourseId());
					});
			});
		
		Map<Integer, Course> courseIdToCourseMap = courseOperation.getCourseIdToCourseMap(courseIds);
		
		System.out.println("**** Student Report Card:- ****\n");
		semesterToReportCardMap
			.entrySet()
			.stream()
			.forEach(entry -> {
				System.out.print(
						  "\n+---------------+\n"
						+ "Semester = " + entry.getKey() 
						+ "\n+---------------+\n");
				
				System.out.println(" CourseId |\tCourseTitle\t| CourseType\t| Dept.\t| Grade\n"
						+ "+------------------------------------------------------------------+");
				
				entry
					.getValue()
					.forEach(reportCard -> {
						System.out.format("    %d\t| %s\t| %s\t| %s\t| %s\n", 
							reportCard.getCourseId(),
							courseIdToCourseMap.get(
								reportCard.getCourseId()).getCourseTitle(), // C Programming Lang.
							courseIdToCourseMap.get(
								reportCard.getCourseId()).getCourseType(),	// PRIMARY						
							courseIdToCourseMap.get(
								reportCard.getCourseId()).getDepartment(),  // CSE, ECE 									
							reportCard.getGrade());						// "A"
					});
				
				System.out.println("+------------------------------------------------------------------+\n");
			});
	}

	@Override
	public void uploadReportCards(List<ReportCard> reportCards) {
		
		if(null == reportCards || reportCards.isEmpty()) {
			return;
		}
		
		// TODO: Should use Batch-insert functionality
		reportCards
			.forEach(reportCard -> {
				if(hasEntry(reportCard)) {
					reportCardDAO.update(reportCard); //particular gradeID in DB.
				} else {
					reportCardDAO.save(reportCard);
				}
			});
		
		System.out.println("Grade Upload Success.");
	}
	
	@Override
	public Map<Integer, ArrayList<ReportCard>> getSemesterToReportCardMapByStudentId(Integer studentId) { //ArrayList of grades
		return reportCardDAO.findAllByStudentId(studentId);

	}
	
	@Override
	public ReportCard getReportCardByStudentForCourse(Student student, Integer courseId) {
		return reportCardDAO.findByStudentAndCourseId(student, courseId);
	}
	
	
	/**************************** Utility Methods *****************************/
	
	private boolean hasEntry(ReportCard reportCard) {
		return null != reportCard.getReportId();
	}

}
