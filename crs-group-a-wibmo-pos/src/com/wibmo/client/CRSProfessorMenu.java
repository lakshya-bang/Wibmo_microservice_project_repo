/**
 * 
 */
package com.wibmo.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.CourseCatalogueOperation;
import com.wibmo.business.CourseCatalogueOperationImpl;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.business.UserOperation;
import com.wibmo.business.UserOperationImpl;
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
		Integer courseId;
		boolean exit = false;
		boolean response;
		int ch;
		
		UserOperation userOperation = new UserOperationImpl();
		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				userOperation, professorOperation);
		CourseRegistrationOperationImpl courseRegistrationOperation =
				new CourseRegistrationOperationImpl(userOperation, courseOperation);
		
		Professor professor = professorOperation.getProfessorById(user.getUserId());
		
		boolean logout = false;
		
		System.out.print("+......... Welcome Professor .........+\n");
		System.out.println(user);
		
		while(!logout) {
			System.out.print("+-------------------------+"
					+ "[0] View Courses Taught\n"
					+ "[1] View Registered Students\n"
					+ "[2] Upload Grades\n"
					+ "[3] Logout\n"
					+ "Enter your choice: ");
			
			ch = in.nextInt();
			
			switch(ch) {
			
			case 0:
				System.out.println("List of Courses Taught: ");
				courseOperation.viewCoursesTaughtByProfessorId(professor.getProfessorId());
				break;
			
			case 1:
				System.out.println("Enter the courseId to view registered students: ");
				// TODO: Should check if this professor is teaching this course
				courseId = in.nextInt();
				System.out.println("*** List of Registered Students:- ***");
				System.out.println(" StudentId    StudentName ");
				System.out.println("+------------------------+");
				courseRegistrationOperation
					.getRegisteredStudentsByCourseId(courseId)
					.forEach()
				break;
				
			case 2:
				// ask the user which course's grade he/she wants to upload
				courseId = in.nextInt();
				
				// TODO: if wrong course selected, i.e.
				// this professor does not teaches any of his assigned courses
				// show message, and break
				
				Map<Integer, String> studentIdToAssignedGradeMap = new HashMap<>();
				String grade = null;
				
				// loop over each student one by one and ask the user to enter the grades.
				courseRegistrationOperation
					.getRegisteredStudentsByCourseId(courseId)
					.forEach(student -> {
						do {
							System.out.print("StudentId: " + student.getStudentId() + ", StudentName: " + student.getStudentName() + ", Enter Grade: ");
							grade = in.next();
						} while(!grade.matches("[ABCDEf|abcdef]"));
						studentIdToAssignedGradeMap.put(student.getStudentId(), grade);
					});
				
				gradeOperation.uploadGrades(courseId, studentIdToAssignedGradeMap);
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
