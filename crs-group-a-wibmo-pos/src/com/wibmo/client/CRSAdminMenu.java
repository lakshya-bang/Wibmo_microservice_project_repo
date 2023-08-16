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
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

/**
 * 
 */
public class CRSAdminMenu {

	public static Boolean display(User user) {
		
		Scanner in = new Scanner(System.in);
		Long courseId;
		boolean exit = false;
		boolean response;
		int ch;
		
		CourseOperation courseOperation = new CourseOperationImpl();
		StudentOperation studentOperation = new StudentOperationImpl();
		AdminOperation adminOperation = new AdminOperationImpl();
		CourseCatalogueOperation courseCatalogueOperation = 
				new CourseCatalogueOperationImpl();
		
		Admin admin = adminOperation.getAdminsByIds(Collections.siuserId);
		Integer semOfStudy = student.getCurrentSemester();
		
		boolean logout = false;
		
		System.out.print("+......... Welcome Student .........+\n");
		System.out.println(user);
		
		while(!logout) {
			System.out.println("+-------------------------+"
					+ "[1] Course Registration\n"
					+ "[2] View Registered Courses\n"
					+ "[3] Add Course\n"
					+ "[4] Drop Course\n"
					+ "[5] View Grades\n"
					+ "[6] Logout\n"
					+ "Enter your choice: ");
			
			ch = in.nextInt();
			
			switch(ch) {
			
			case 1:
				System.out.println("*** COURSE CATALOGUE:- ***");
				courseCatalogueOperation.viewCourseCatalogue();
				Set<Long> selectedCourses = new HashSet<>();
				// 4 Primary Courses to be selected:
				System.out.println("Enter 4 Primary Course Ids: ");
				for(int i = 1; i <= 4; i++) {
					selectedCourses.add(in.nextLong());
				}
				// 2 Alternative Courses to be selected:
				System.out.println("Enter 2 Alternative Course Ids: ");
				for(int i = 1; i <= 2; i++) {
					selectedCourses.add(in.nextLong());
				}
				try {
					student.registerCourses(userId, selectedCourses);
				} catch (StudentNotFoundException | CoursesNotAvailableForRegistrationException
						| StudentAlreadyRegisteredForSemesterException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				break;
				
			case 2:
				// TODO: Should be viewRegisteredCoursesBy...() instead
				student.viewRegisteredCourseIdsBySemOfStudy(userId, semOfStudy);
				break;
				
			case 3:
				System.out.println("*** Course Catalogue:- ***");
				courseCatalogueOperation.viewCourseCatalogue();
				System.out.print("Enter Id of the course you wish to add: ");
				courseId = in.nextLong();
				student.addCourse(courseId, semOfStudy);
				break;
				
			case 4:
				System.out.println("--- Registered Courses:- ---");
				student.getRegisteredCourseIdsBySemOfStudy(userId, semOfStudy);
				System.out.print("Enter Id of the course you wish to drop: ");
				courseId = in.nextLong();
				student.dropCourse(courseId, semOfStudy);
				break;
				
			case 5:
				gradeOperation.viewGradesByStudentId(userId);
				break;
				
			case 6:
				logout = true;
				break;
				
			default:
				System.out.println("Invalid choice! Try again.");
			}
		}
		return Boolean.FALSE;
	}
	
}
