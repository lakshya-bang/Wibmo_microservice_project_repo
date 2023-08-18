package com.wibmo.client;

import java.util.Scanner;
import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
import com.wibmo.bean.User;
import com.wibmo.business.AdminOperation;
import com.wibmo.business.AdminOperationImpl;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.business.UserOperation;
import com.wibmo.business.UserOperationimpl;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;

/**
 * 
 */
public class CRSAdminMenu {

	public static Boolean display(Scanner input, User user) {
		
		Integer userId, courseId;
		boolean logout = false, exit = false;
		int choice;

		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		UserOperation userOperation  =new UserOperationimpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				professorOperation);
		CourseRegistrationOperation courseRegistrationOperation =
				new CourseRegistrationOperationImpl(
						studentOperation,
						professorOperation,
						courseOperation);
		AdminOperation adminOperation = new AdminOperationImpl();
		
		Admin admin = adminOperation.getAdminById(user.getUserId());
		
		System.out.print("+......... Welcome Admin .........+\n");
		System.out.println(admin);

		while(!logout) {
			System.out.print("+-------------------------+\n"
					+ "[1] Approve / Reject New Account Registration\n"
					+ "[2] Approve / Reject Student Course Registration\n"
					+ "[3] View Course Catalogue\n"
					+ "[4] Add Course to Catalogue\n"
					+ "[5] Drop Course from Catalogue\n"
					+ "[6] Assign course to Professor\n"
					+ "[7] Generate Report Cards\n"
					+ "[8] Send Bill Due Notification\n"
					+ "[9] Logout\n"
					+ "Enter your choice: ");
					// TODO: Bill Functionality will be added later
			
			choice = input.nextInt();
			
			switch(choice) {
			
			// TODO: This will be moved to Registration section of the CRS Application 
//			case 1:
//				System.out.println("*** Register New Admin ***");
//				System.out.println("Please enter new Admin password: ");
//				String password = in.next();
//				System.out.println("Please enter the name : ");
//				String name = in.next();
//				adminOperation.registerAdmin(email, password, name);
//				break;
				
			case 1:
				System.out.println("*** New Account Registrations:- ***");
				userOperation.viewAccountsPendingForApproval();
				
				exit = false;
				
				while(!exit) {
					
					System.out.print("Enter the User Id: ");
					// TODO: Should add check if this user id even exists
					userId = input.nextInt();
					
					System.out.print("Choose Action:\n"
							+ "[1] Approve\n"
							+ "[2] Reject\n"
							+ "[3] Exit\n"
							+ "Enter your choice: ");
					choice = input.nextInt();
					
					switch(choice) {
					case 1:
						userOperation.approveLoginById(userId);
						break;
						
					case 2:
						userOperation.rejectLoginById(userId);
						break;
						
					case 3:
						exit = true;
						break;
						
					default:
						System.out.println("Invalid choice, Please try again.");
					}
					
				}
				break;
			
			case 2:
				System.out.println("*** Pending Student Course Registrations:- ***");
				
				courseRegistrationOperation
					.viewCourseRegistrationByRegistrationStatus(
							RegistrationStatus.PENDING);
				
				exit = false;
				
				while(!exit) {
					
					System.out.print("Enter the Course Registration Id: ");
					// TODO: Should add check if this user id even exists
					Integer courseRegId = input.nextInt();
					
					System.out.print("Choose Action:\n"
							+ "[1] Approve\n"
							+ "[2] Reject\n"
							+ "[3] Exit\n"
							+ "Enter your choice: ");
					choice = input.nextInt();
					
					switch(choice) {
					case 1:
						courseRegistrationOperation
							.approveRegistrationByRegistrationId(
								courseRegId);
						break;
						
					case 2:
						courseRegistrationOperation
							.rejectRegistrationByRegistrationId(
									courseRegId);
						break;
						
					case 3:
						exit = true;
						break;
						
					default:
						System.out.println("Invalid choice, Please try again.");
					}
					
				}
				
				break;
				
			case 3:
				courseOperation.viewAllCourses();
				break;
				
			case 4:
				System.out.println("--- Add a new Course to catalog:- ---");
				System.out.print("Enter Title of the Course: ");
				String courseTitle = input.next();
				System.out.print("Enter Year of the Course: ");
				Integer courseYear = input.nextInt();
				System.out.print("Enter Semester of the Course: ");
				Integer semester = input.nextInt();
				System.out.print("Enter Department of the Course: ");
				String department = input.next();
				
				CourseType courseType=null;
				
				exit = false;
				
				while(!exit) {
					System.out.print("Choose Type of the course:\n"
							+ "[1] Primary\n"
							+ "[2] Alternative\n"
							+ "Enter your choice: ");
					
					choice = input.nextInt();
					
					switch(choice) {
					case 1:
						courseType = CourseType.PRIMARY;
						exit = true;
						break;
					case 2:
						courseType = CourseType.ALTERNATIVE;
						exit = true;
						break;
					default:
						System.out.println("Invalid choice! Please try again.");
					}
				}
				
				Course course = new Course();
				course.setCourseTitle(courseTitle);
				course.setDepartment(department);
				course.setYear(courseYear);
				course.setSemester(semester);
				course.setCourseType(courseType);
				
//				adminOperation.addCourseToCatalog(course);
				courseOperation.addCourse(course);
				
				break;
				
			case 5:
				System.out.println("--- Drop Course from catalog:- ---");
				
//				System.out.println("Please enter course name: ");
//				courseName = input.next();
				
				System.out.print("Enter Course Id: ");
				courseId = input.nextInt();
				
//				adminOperation.dropCourseFromCatalog(courseName);
				courseOperation.removeCourseById(courseId);
				
				break;
				
			case 6:
				System.out.println("--- Assign course to professor:- ---");
				
//				System.out.println("Please enter the course name: ");
//				courseName = in.next();
//				System.out.println("Please enter Professor name: ");
//				String professorName = in.next();
				System.out.print("Enter Course Id: ");
				courseId = input.nextInt();
				System.out.println("Enter Professor Id: ");
				Integer professorId = input.nextInt();
				
//				adminOperation.assignCoursesToProfessor(courseName,professorName);
				courseOperation.assignCourseToProfessor(
						courseId, professorId);
				
				break;
			
			case 7:
				System.out.println("Missing Functionality.");
				break;
				
			case 8:
				System.out.println("Missing Functionality.");
				break;
				
			case 9:
				logout = true;
				break;
				
			default:
				System.out.println("Invalid choice! Try again.");
			}
		}
		
		return Boolean.FALSE;
	}
	
}
