package com.wibmo.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
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
					+ "[1] LogIn\n"
					+ "[2] Register Admin to CRS\n"
					+ "[3] Approve Student Registration\n"
					+ "[4] Add Course to Catalogue\n"
					+ "[5] Drop Course from Catalogue\n"
					+ "[6] Assign course to Professor\n"
					+ "[7] Generate Report card\n"
					+ "Enter your choice: ");
					// Bill Functionality -> move
			
			ch = in.nextInt();
			
			switch(ch) {
			
			case 1:
				System.out.println("***LogIn***");
				adminOperation.logIn();
				break;
				
			case 2:System.out.println("***Register New Admin***");
				System.out.println("Please enter new Admin email: ");
				String email = in.next();
				System.out.println("Please enter new Admin password: ");
				String password = in.next();
				System.out.println("Please enter the name : ");
				String name = in.next();
				adminOperation.registerAdmin(email, password, name);
				break;
				
			case 3:
				// System.out.println("*** Course Catalogue:- ***");
				// courseCatalogueOperation.viewCourseCatalogue();
				// System.out.print("Enter Id of the course you wish to add: ");
				// courseId = in.nextLong();
				// student.addCourse(courseId, semOfStudy);
				break;
				
			case 4:
				System.out.println("--- Add a new Course to catalog:- ---");
				System.out.println("Please enter course name: ");
				String courseName = in.next();
				System.out.println("Please enter year of the course: ");
				int courseYear = in.nextInt();
				System.out.println("Please enter semester of the course: ");
				int semester = in.nextInt();
				System.out.println("Please enter department of the course: ");
				String department = in.next();
				System.out.println("Please enter type of the course: ");
				String courseType = in.next();
				
				Course course = new Course();
				course.setName(courseName);
				course.setDepartment(department);
				course.setYear(courseYear);
				course.setSemester(semester);
				course.setCourseType(courseType);
				adminOperation.addCourseToCatalog(course);
				break;
				
			case 5:
				System.out.println("--- Drop Course to catalog:- ---");
				System.out.println("Please enter course name: ");
				courseName = in.next();
				adminOperation.dropCourseFromCatalog(courseName);
			break;
				
			case 6:
				System.out.println("--- Assign course to professor:- ---");
				System.out.println("Please enter the course name: ");
				courseName = in.next();
				System.out.println("Please enter Professor name: ");
				String professorName = in.next();
				adminOperation.assignCoursesToProfessor(courseName,professorName);
			break;
				
			default:
				System.out.println("Invalid choice! Try again.");
			}
		}
		return Boolean.FALSE;
	}
	
}
