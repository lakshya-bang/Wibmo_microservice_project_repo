/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.dao.ProfessorDAOImpl;

/**
 * 
 */
public class ProfessorBusinessImpl implements ProfessorBusiness{

	@Override
	public void viewEnrolledStudents(Professor professor) {
		ProfessorDAOImpl professorDao = ProfessorDAOImpl.getInstance();
		Map<Integer,ArrayList<Integer>> studentMap = professorDao.getStudentList(professor.getCoursesTaught());
		for(Map.Entry<Integer, ArrayList<Integer>> entry : studentMap.entrySet()) {
			System.out.println("For Course with ID - " + entry.getKey());
			System.out.println("List of Students - " + entry.getValue());
			System.out.println("========================================");
		}
	}

	@Override
	public Boolean updateGradeDetails() {
		ProfessorDAOImpl professorDao = ProfessorDAOImpl.getInstance();
		Scanner in = new Scanner(System.in);
		Map<Integer,Character> grades = new HashMap<Integer,Character>();
		System.out.println("Please enter the studentId: ");
		Integer studentId = in.nextInt();
		System.out.println("Please enter the semester: ");
		Integer semester = in.nextInt();
		System.out.println("Please enter the year: ");
		Integer year = in.nextInt();
		System.out.println("Please enter the courseID along with the grade: ");
		grades.put(in.nextInt(),in.next().charAt(0));
		for(int i = 0; i<3; i++) {
			System.out.println("Next entry: ");
			grades.put(in.nextInt(),in.next().charAt(0));
		}
		return professorDao.updateGrades(grades, year, semester, new Random().nextInt(100), studentId);
	}
	
}
