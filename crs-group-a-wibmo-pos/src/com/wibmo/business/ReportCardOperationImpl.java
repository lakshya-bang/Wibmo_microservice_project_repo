/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
	
	private final StudentOperation studentOperation;
	
	private final CourseRegistrationOperation courseRegistrationOperation;
	
	private final ReportCardDAO gradeDAO;
	
	public ReportCardOperationImpl(
			StudentOperation studentOperation,
			CourseOperation courseOperation,
			CourseRegistrationOperation courseRegistrationOperation) {
		this.studentOperation = studentOperation;
		this.courseOperation = courseOperation;
		this.courseRegistrationOperation = courseRegistrationOperation;
		gradeDAO = ReportCardDAOImpl.getInstance();
	}
	
	@Override
	public void viewReportCardByStudent(Student student) {
		
		Map<Integer, ArrayList<ReportCard>> semesterToGradeMap = getSemesterToReportCardMapByStudentId(student.getStudentId());
		ArrayList<Integer> courseIds = new ArrayList<>();
		for(ArrayList<ReportCard> temp : semesterToGradeMap.values()){
			for(ReportCard grade : temp){
				courseIds.add(grade.getCourseId());
			}
		}
		Map<Integer, Course> courseIdToCourseMap = courseOperation
				.getCourseIdToCourseMap(new HashSet<>(courseIds));

		System.out.println("**** Student Grades:- ****");
		semesterToGradeMap
			.entrySet()
			.stream()
			.forEach(entry -> {
				System.out.print(
						"\n-------------------\n"
						+ "Semester = " + entry.getKey() 
						+ "\n---------------\n");
				
				System.out.println(" CourseId    CourseTitle    Department    Grade ");
				System.out.println("+----------------------------------------------+");
				for(ReportCard grade : entry.getValue()){
						System.out.format("%5d%10s%10s%10s", 
						grade.getCourseId(),
						courseIdToCourseMap.get(
								grade.getCourseId()).getCourseTitle(),  // course title
						courseIdToCourseMap.get(
								grade.getCourseId()).getDepartment(),   // CSE, ECE 
						grade.getGrade());    // "A"
				}
				
			});
	}

	@Override
	public void uploadReportCards(List<ReportCard> reportCards) {
		
		if(null == reportCards || reportCards.isEmpty()) {
			return;
		}
		
		// Filter out New Report Cards
		List<ReportCard> newReportCards = reportCards
				.stream()
				.filter(reportCard -> null == reportCard.getReportId())
				.collect(Collectors.t());
		
		
		if(hasEntry(reportCard)) {
			gradeDAO.updateByGradeDetails(reportCard); //particular gradeID in DB.
		} else {
			gradeDAO.save(reportCard);
		}
	}
	
	@Override
	public Map<Integer, ArrayList<ReportCard>> getSemesterToReportCardMapByStudentId(Integer studentId) { //ArrayList of grades
		return gradeDAO.findAllByStudentId(studentId);

	}
	
	private boolean hasEntry(ReportCard reportCard) {
		if(reportCard.getCourseId()!=null){
			return gradeDAO.checkGradeDetails(reportCard);
		}
		else{
			return false;
		}
	}

}
