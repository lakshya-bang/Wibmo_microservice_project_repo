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
import com.wibmo.bean.Grade;
import com.wibmo.bean.Student;
import com.wibmo.dao.GradeDAO;
import com.wibmo.dao.GradeDAOImpl;

/**
 * 
 */
public class GradeOperationImpl implements GradeOperation {

	private final CourseOperation courseOperation;
	
	private final GradeDAO gradeDAO;
	
	public GradeOperationImpl(CourseOperation courseOperation) {
		this.courseOperation = courseOperation;
		gradeDAO = GradeDAOImpl.getInstance();
	}
	
	@Override
	public void viewGradesByStudent(Student student) {
		
		Map<Integer, ArrayList<Grade>> semesterToGradeMap = getSemesterToGradeMapByStudentId(student.getStudentId());
		ArrayList<Integer> courseIds = new ArrayList<>();
		for(ArrayList<Grade> temp : semesterToGradeMap.values()){
			for(Grade grade : temp){
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
				for(Grade grade : entry.getValue()){
						System.out.format("%5d%10s%10s%10s", 
						grade.getCourseId(),
						courseIdToCourseMap.get(
								grade.getCourseId()).getName(),  // course title
						courseIdToCourseMap.get(
								grade.getCourseId()).getDepartment(),   // CSE, ECE 
						grade.getGrade());    // "A"
				}
				
			});
	}

	@Override
	public void uploadGrade(Grade grade) {
		if(hasEntry(grade)) {
			gradeDAO.updateByGradeId(grade); //particular gradeID in DB.
		} else {
			gradeDAO.save(grade);
		}
	}
	
	@Override
	public Map<Integer, ArrayList<Grade>> getSemesterToGradeMapByStudentId(Integer studentId) { //ArrayList of grades
		return gradeDAO.findAllByStudentId(studentId);
	
	}
	
	private boolean hasEntry(Grade grade) {
		if(grade.getGradeId()!=null){
			return gradeDAO.checkGradeDetails(grade);
		}
		else{
			return false;
		}
	}

}
