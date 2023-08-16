/**
 * 
 */
package com.wibmo.business;

import java.util.Map;
import java.util.function.Function;
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
		gradeDAO = new GradeDAOImpl();
	}
	
	@Override
	public void viewGradesByStudent(Student student) {
		
		Map<Integer, Grade> semesterToGradeMap = getSemesterToGradeMapByStudentId(student.getStudentId());
		Map<Integer, Course> courseIdToCourseMap = courseOperation
				.getCourseIdToCourseMap(
						semesterToGradeMap
							.entrySet()
							.stream()
							.map(entry -> entry.getValue().getCourseId())
							.collect(Collectors.toSet()));
		
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
				Grade grade = entry.getValue();
				System.out.format("%5d%10s%10s%10s", 
						grade.getCourseId(),
						courseIdToCourseMap.get(
								grade.getCourseId()).getCourseTitle(),  // course title
						courseIdToCourseMap.get(
								grade.getCourseId()).getDepartment(),   // CSE, ECE 
						grade.getGrade());    // "A"
			});
		
		
	}

	@Override
	public void uploadGrades(Integer courseId, Map<Integer, String> studentIdToAssignedGradesMap) {
		
		// TODO: Lakshya is doing
		
		if(hasEntry(grade)) {
			gradeDAO.updateByGradeId(grade.getGradeId());
		} else {
			gradeDAO.save(grade);
		}
	}
	
	@Override
	public Map<Integer, Grade> getSemesterToGradeMapByStudentId(Integer studentId) {
		return gradeDAO
			.findAllByStudentId(studentId)
			.stream()
			.collect(Collectors.toMap(
					Grade::getSemester,    	// key (semester)
					Function.identity()));	// Grade object
	
	}
	
	private boolean hasEntry(Grade grade) {
		if(grade.getGradeId())
	}

}
