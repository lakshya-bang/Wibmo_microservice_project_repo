package com.wibmo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ReportCardOperation;
import com.wibmo.business.ReportCardOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;

/**
 * Displays the Menu for Student
 */
public class CRSStudentMenu {

	public static Boolean display(Scanner input, User user) {
		
		Integer courseId;
		boolean logout = false;
		int choice;

		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				professorOperation);
		StudentOperation studentOperation = new StudentOperationImpl();
		CourseRegistrationOperation courseRegistrationOperation = 
				new CourseRegistrationOperationImpl(
						studentOperation, professorOperation, courseOperation);
		ReportCardOperation reportCardOperation = new ReportCardOperationImpl(courseOperation);

		Student student = studentOperation.getStudentById(user.getUserId());

		System.out.print("+......... Welcome Student .........+\n");
		System.out.println("Student Id : " + student.getStudentId());
		System.out.println("Student Name : " + student.getStudentName());
		System.out.println("Current Semester : " + student.getCurrentSemester());

		while (!logout) {
			System.out.print("+-------------------------+\n" 
					+ "[0] View Course Catalogue\n"
					+ "[1] Course Registration\n"
					+ "[2] View Registered Courses\n"
					+ "[3] View Registration Status\n"
					+ "[4] Add Course\n" 
					+ "[5] Drop Course\n" 
					+ "[6] View Grades\n"
					+ "[7] Pay Bill\n" 
					+ "[8] Logout\n" 
					+ "Enter your choice: ");
			
			while(!input.hasNextInt());
			
			choice = input.nextInt();

			switch (choice) {

			case 0:
				System.out.println("*** Course Catalogue:- ***");
				courseOperation.viewCourseDetailsBySemester(student.getCurrentSemester());
				break;
				
			case 1:
				
				List<Integer> primaryCourses = new ArrayList<>();
				List<Integer> alternativeCourses = new ArrayList<>();
				
				// 4 Primary Courses to be selected:
				System.out.println("Enter 4 Primary Course Ids: ");
				while(primaryCourses.size() != 4) {
					courseId = input.nextInt();
					if(primaryCourses.contains(courseId)) {
						System.out.println("Already selected, choose another.");
					} else {
						primaryCourses.add(courseId);
					}
				}
				System.out.println("Primary Courses Selected: " + primaryCourses);
				
				// 2 Alternative Courses to be selected:
				System.out.println("Enter 2 Alternative Course Ids: ");
				while(alternativeCourses.size() != 2) {
					courseId = input.nextInt();
					if(alternativeCourses.contains(courseId)) {
						System.out.println("Already selected, choose another.");
					} else {
						alternativeCourses.add(courseId);
					}
				}
				System.out.println("Alternative Courses Selected: " + alternativeCourses);
				
				try {
					courseRegistrationOperation.register(
							primaryCourses, 
							alternativeCourses,
							student);
					
					System.out.println("Successfully Registered!");
					
				} catch (StudentAlreadyRegisteredForSemesterException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				break;

			case 2:
				try {
					courseRegistrationOperation
						.viewRegisteredCoursesByStudent(student);
				} catch (StudentNotRegisteredForSemesterException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				break;

			case 3:
				try {
					System.out.println("Your Registration Status = " + 
							courseRegistrationOperation
								.getRegistrationStatusByStudent(student));
				} catch (StudentNotRegisteredForSemesterException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				break;
				
			case 4:
				System.out.print("Enter Id of the course you wish to add: ");
				courseId = input.nextInt();

				try {
					courseRegistrationOperation.addCourse(courseId, student);
				} catch (StudentNotRegisteredForSemesterException 
						| StudentAlreadyRegisteredForCourseInSemesterException
						| StudentAlreadyRegisteredForAllAlternativeCoursesException
						| StudentAlreadyRegisteredForAllPrimaryCoursesException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				break;

			case 5:
				System.out.print("Enter Id of the course you wish to drop: ");
				courseId = input.nextInt();
				
				try {
					courseRegistrationOperation.dropCourse(courseId, student);
				} catch (StudentNotRegisteredForSemesterException
						| StudentNotRegisteredForCourseInSemesterException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				
				break;

			case 6:
				reportCardOperation.viewGradesByStudent(student);
				break;

			case 7:
				System.out.println("Missing Functionality :(");
				break;

			case 8:
				logout = true;
				break;

			default:
				System.out.println("Invalid choice! Try again.");

			}
		}
		
		return Boolean.FALSE;
	}
}
