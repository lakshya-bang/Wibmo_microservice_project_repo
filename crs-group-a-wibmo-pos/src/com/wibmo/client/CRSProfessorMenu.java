/**
 * 
 */
package com.wibmo.client;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.CourseCatalogueOperation;
import com.wibmo.business.CourseCatalogueOperationImpl;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

/**
 * 
 */
public class CRSProfessorMenu {
	
	public static Boolean display(User user) {
		
		Scanner in = new Scanner(System.in);
		Long courseId;
		boolean exit = false;
		boolean response;
		int ch;
		
		CourseOperation courseOperation = new CourseOperationImpl();
		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		CourseCatalogueOperation courseCatalogueOperation = 
				new CourseCatalogueOperationImpl();
		
		Professor professor = professorOperation.getProfessorById(userId);
		
		boolean logout = false;
		
		System.out.print("+......... Welcome Professor .........+\n");
		System.out.println(user);
		
		while(!logout) {
			System.out.print("+-------------------------+"
					+ "[1] View Enrolled Students\n"
					+ "[2] Upload Grades\n"
					+ "[3] Logout\n"
					+ "Enter your choice: ");
			
			ch = in.nextInt();
			
			switch(ch) {
			
			case 1:
				System.out.println("*** Enrolled Students:- ***");
				// get enrolled students grouped by course
				
			case 2:
				// get courses taught by professor
				Set<Long> courseIds = professorOperation.getCoursesTaught(user.getUserId());
				
				// ask the user which course's grade he/she wants to upload
				courseId = in.nextLong();
				
				// TODO: if wrong course selected, show message, and break
				
				// get list of student Ids enrolled in that course
				
				// loop over each student one by one and ask the user to enter the grades.
				
				// send courseID, Map<studentId, Grade> to the upload grades parameter.
				
				gradeOperation.uploadGrades(courseId, studentToGradesMapping);
				break;
				
			case 3:
				logout = true;
				break;
				
			default:
				System.out.println("Invalid choice! Try again.");
				
			}
		}
		
		return Boolean.FALSE;
	}
}
