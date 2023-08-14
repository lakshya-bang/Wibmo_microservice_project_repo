package com.wibmo.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.wibmo.bean.Professor;
import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.business.FakeAuthenticationService;
import com.wibmo.business.ProfessorBusinessImpl;
import com.wibmo.dao.AuthenticationDaoImpl;
import com.wibmo.dao.*;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		char ch = '\0';
		
		StudentDAOImpl fakeStudent = StudentDAOImpl.getInstance();
		
		fakeStudent.registerCourse(1005L, List.of(101,102,103));
		fakeStudent.viewRegisteredCourses(1005L);
		
		ProfessorDAO professorDao = ProfessorDAOImpl.getInstance();
		System.out.println(professorDao.fetchCoursesTaught(1, 1, 1));
		
		Professor professor = new Professor();
		
		professor.setCoursesTaught(new ArrayList<Integer>(Arrays.asList(2,3)));
		ProfessorBusinessImpl professorBusiness = new ProfessorBusinessImpl();
		professorBusiness.viewEnrolledStudents(professor);
		Map<Integer,Character> grades = new HashMap<Integer,Character>();
		grades.put(1, 'A');
		grades.put(2, 'B');
		grades.put(3, 'C');
		professorBusiness.updateGradeDetails();
	}
}
