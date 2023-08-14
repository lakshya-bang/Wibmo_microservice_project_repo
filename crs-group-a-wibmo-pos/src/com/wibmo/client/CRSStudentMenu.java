package com.wibmo.client;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.CourseCatalogueOperation;
import com.wibmo.business.CourseCatalogueOperationImpl;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

public class CRSStudentMenu {
	
	public static Boolean display(User user) {
		
		Scanner in = new Scanner(System.in);
		Integer courseId;
		boolean exit = false;
		boolean response;
		int ch;
		
		CourseOperation courseOperation = new CourseOperationImpl();
		StudentOperation studentOperation = new StudentOperationImpl();
		CourseCatalogueOperation courseCatalogueOperation = 
				new CourseCatalogueOperationImpl();
		
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
					+ "[6] Pay Bill\n"
					+ "[7] Logout\n"
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
				studentOperation.registerCourses(user.getUserId(), selectedCourses);
//				try {
//					studentOperation.registerCourses(user.getUserId(), selectedCourses);
//				} catch (StudentNotFoundException | CoursesNotAvailableForRegistrationException
//						| StudentAlreadyRegisteredForSemesterException e) {
//					System.out.println(e.getMessage());
////					e.printStackTrace();
//				}
				break;
				
			case 2:
				// TODO: Should be viewRegisteredCoursesBy...() instead
//				studentOperation.viewRegisteredCourseIdsBySemOfStudy(userId, semOfStudy);
				studentOperation.viewRegisteredCourses(user.getUserId());
				break;
				
			case 3:
				System.out.println("*** Course Catalogue:- ***");
				courseCatalogueOperation.viewCourseCatalogue();
				System.out.print("Enter Id of the course you wish to add: ");
				courseId = in.nextInt();
				studentOperation.addCourse(courseId, user.getUserId());
				break;
				
			case 4:
				System.out.println("--- Registered Courses:- ---");
				studentOperation.viewRegisteredCourses(user.getUserId());
				System.out.print("Enter Id of the course you wish to drop: ");
				courseId = in.nextInt();
				studentOperation.dropCourse(courseId, user.getUserId());
				break;
				
			case 5:
//				gradeOperation.viewGradesByStudentId(userId);
				break;
			
			case 6:
//				billOperation.payBill(userId);
				break;
				
			case 7:
				logout = true;
				break;
				
			default:
				System.out.println("Invalid choice! Try again.");
				
			}
		}
		return Boolean.FALSE;
	}
}
