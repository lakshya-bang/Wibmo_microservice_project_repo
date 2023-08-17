/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;
import com.wibmo.dao.ReportCardDAO;
import com.wibmo.dao.ReportCardDAOImpl;

/**
 * 
 */
public class ReportCardOperationImpl implements ReportCardOperation {

	private final CourseOperation courseOperation;
	
	private final ReportCardDAO gradeDAO;
	
	public ReportCardOperationImpl(CourseOperation courseOperation) {
		this.courseOperation = courseOperation;
		gradeDAO = ReportCardDAOImpl.getInstance();
	}
	
	@Override
	public void viewGradesByStudent(Student student) {
		
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
	public void uploadGrades(ReportCard reportCard) {
		
		// TODO: Lakshya is doing
		
		if(hasEntry(reportCard)) {
			gradeDAO.updateByGradeId(reportCard); //particular gradeID in DB.
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
