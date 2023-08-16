package com.wibmo.client;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.AdminOperation;
import com.wibmo.business.AdminOperationImpl;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

/**
 * 
 */
public class CRSAdminMenu {

	public static Boolean display(User user) {
		
		Scanner input = new Scanner(System.in);
		Long courseId;
		boolean logout = false;
		boolean response;
		int choice;
		
		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				professorOperation);
		AdminOperation adminOperation = new AdminOperationImpl();
		
		Admin admin = adminOperation.getAdminById(user.getUserId());
		
		System.out.print("+......... Welcome Student .........+\n");
		System.out.println(admin);
		
		
		
		input.close();
		return Boolean.FALSE;
	}
	
}
