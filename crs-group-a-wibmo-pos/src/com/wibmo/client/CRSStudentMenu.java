package com.wibmo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

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
import com.wibmo.dao.AuthenticationDAOImpl;
import com.wibmo.exception.CourseNotExistsInCatalogException;
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

	// Plug Logger in CRSStudentMenu logger injection
	private static final Logger LOG = Logger.getLogger(CRSStudentMenu.class);
	
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
		ReportCardOperation reportCardOperation = new ReportCardOperationImpl(
				studentOperation, courseOperation);

		Student student = studentOperation.getStudentById(user.getUserId());

		LOG.info("+......... Welcome Student .........+\n"
				+ "Student Id : " + student.getStudentId() + "\n"
				+ "Student Name : " + student.getStudentName() + "\n"
				+ "Student Email : " + student.getStudentEmail() + "\n"
				+ "Current Semester : " + student.getCurrentSemester());
		
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
				
				// TODO: Move to Validator
				
				// 4 Primary Courses to be selected:
				System.out.println("Enter 4 Primary Course Ids: ");
				while(primaryCourses.size() != 4) {
					courseId = input.nextInt();
					if(primaryCourses.contains(courseId)) {
						System.out.println(courseId + " already selected, choose another.");
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
						System.out.println(courseId + " already selected, choose another.");
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
					
				} catch (StudentAlreadyRegisteredForSemesterException 
						| CourseNotExistsInCatalogException e) {
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
						| StudentAlreadyRegisteredForAllPrimaryCoursesException 
						| CourseNotExistsInCatalogException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();z
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
				reportCardOperation.viewReportCardByStudent(student);
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
