/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.wibmo.bean.Grade;
import com.wibmo.bean.Professor;
import com.wibmo.dao.CourseRegistrationDAO;
import com.wibmo.dao.CourseRegistrationDAOImpl;
import com.wibmo.dao.GradeDAO;
import com.wibmo.dao.ReportCardDAOImpl;

/**
 * 
 */
public class ProfessorBusinessImpl implements ProfessorBusiness{

	@Override
	public void viewEnrolledStudents(Professor professor) {
		CourseRegistrationDAO courseRegistrationDao = new CourseRegistrationDAOImpl();
		Map<Integer, ArrayList<Integer>> studentIDLists =courseRegistrationDao.getStudentsByCourseId(professor);;
		for(Map.Entry<Integer,ArrayList<Integer>> entry : studentIDLists.entrySet() ){
			System.out.println("For courseID - " + entry.getKey() + "\nThe registered students are - ");
			System.out.println(entry.getValue());
			System.out.println("----------------------------------------");
		}
		System.out.println("========================================================");
	}

	@Override
	public void updateGradeDetails() {
		GradeDAO gradesDao = ReportCardDAOImpl.getInstance();
		Scanner in = new Scanner(System.in);
		Grade grade = new Grade();
		System.out.println("Please enter the studentId: ");
		Integer studentId = in.nextInt();
		grade.setStudentId(studentId);
		System.out.println("Please enter the semester: ");
		Integer semester = in.nextInt();
		grade.setSemester(semester);
		System.out.println("Please enter the year: ");
		Integer year = in.nextInt();
		grade.setYear(year);
		System.out.println("Please enter the courseID along with the grade: ");
		Integer courseID = in.nextInt();
		grade.setCourseId(courseID);
		String gradeOfCourse = in.next();
		grade.setGrade(gradeOfCourse);
		gradesDao.save(grade);
	}
	
}
