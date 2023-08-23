package com.wibmo.client;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

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
import com.wibmo.business.UserOperationImpl;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;

/**
 * 
 */
public class CRSAdminMenu {

	// Plug Logger in CRSStudentMenu logger injection
	private static final Logger LOG = Logger.getLogger(CRSAdminMenu.class);
	
	public static Boolean display(Scanner input, User user) {
		
		String str;
		Integer userId, courseId;
		boolean logout = false, exit = false;
		int choice;
		Boolean response;
		Set<Integer> userIds = new HashSet<>();
		Set<Integer> courseRegistrationIds = new HashSet<>();

		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		UserOperation userOperation  =new UserOperationImpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				professorOperation);
		CourseRegistrationOperation courseRegistrationOperation =
				new CourseRegistrationOperationImpl(
						studentOperation,
						professorOperation,
						courseOperation);
		AdminOperation adminOperation = new AdminOperationImpl();
		
		Admin admin = adminOperation.getAdminById(user.getUserId());
		
		LOG.info("+......... Welcome Admin .........+\n"
				+ "Admin Id : " + admin.getAdminId() + "\n"
				+ "Admin Name : " + admin.getAdminName() + "\n"
				+ "Admin Email : " + admin.getAdminEmail());

		while(!logout) {
			System.out.print("+---------------------------------+\n"
					+ "[0] View New Account Registration Requests\n"
					+ "[1] Approve / Reject New Account Registration\n"
					+ "[2] View New Course Registration Requests\n"
					+ "[3] Approve / Reject Student Course Registration\n"
					+ "[4] View Course Catalogue\n"
					+ "[5] Add Course to Catalogue\n"
					+ "[6] Drop Course from Catalogue\n"
					+ "[7] Assign course to Professor\n"
//					+ "[7] Generate Report Cards\n"
					+ "[8] Send Bill Due Notification\n"
					+ "[9] Logout\n"
					+ "Enter your choice: ");
			
			while(!input.hasNextInt());
			choice = input.nextInt();
			
			switch(choice) {
				
			case 0:
				System.out.println("*** New Account Registrations:- ***");
				userOperation.viewAccountsPendingForApproval();
				break;
			
			case 1:
				exit = false;
				
				while(!exit) {
					
					System.out.print("+----------------------------+\n"
							+ "[1] Approve Specific Accounts\n"
							+ "[2] Approve All\n"
							+ "[3] Reject Specific Accounts\n"
							+ "[4] Reject All\n"
							+ "[5] Exit\n"
							+ "Enter your choice: ");
					
					choice = input.nextInt();
					
					switch(choice) {
					case 1:
						System.out.println("Enter User Ids to Approve: ");
						userIds.clear();
						
						while (true) {
			            	
							while(!input.hasNextInt());
							
			                userIds.add(input.nextInt());

			                do {
			                	System.out.print("Continue ? (Y/N): ");
			                	str = input.next();
			                } while(!str.matches("[YN|yn]"));
			                
			                if(str.matches("[N|n]")) {
			                	break;
			                }
			            }
						
						response = userOperation.updateAccountRegistrationStatusToByUserIds(
								RegistrationStatus.APPROVED,
								userIds);
						

						if(Boolean.TRUE.equals(response)) {
							System.out.println("User Id(s) have been " 
									+ RegistrationStatus.APPROVED.toString() + ".");
						} else {
							System.out.println("Request Failure!");
						}
						
						break;
						
					case 2:
						response = userOperation.updateAllPendingAccountRegistrationsTo(
								RegistrationStatus.APPROVED);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("All Pending User Id(s) have been " 
									+ RegistrationStatus.APPROVED.toString() + ".");
						} else {
							System.out.println("Request Failure!");
						}
						
						break;
						
					case 3:
						System.out.println("Enter User Ids to Reject: ");
						userIds.clear();
						
						while (true) {
			            	
							while(!input.hasNextInt());
							
			                userIds.add(input.nextInt());

			                do {
			                	System.out.print("Continue ? (Y/N): ");
			                	str = input.next();
			                } while(!str.matches("[YN|yn]"));
			                
			                if(str.matches("[N|n]")) {
			                	break;
			                }
			            }
						
						response = userOperation.updateAccountRegistrationStatusToByUserIds(
								RegistrationStatus.REJECTED,
								userIds);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("User Id(s) have been " 
									+ RegistrationStatus.REJECTED.toString() + ".");
						} else {
							System.out.println("Request Failure!");
						}
						
						break;
						
					case 4:
						response = userOperation.updateAllPendingAccountRegistrationsTo(
								RegistrationStatus.REJECTED);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("All Pending User Id(s) have been " 
									+ RegistrationStatus.REJECTED.toString() + ".");
						} else {
							System.out.println("Request Failure!");
						}
						
						break;
						
					case 5:
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
				
				break;
				
			case 3:
				
				exit = false;
				
				while(!exit) {
					
					System.out.print("+--------------------------+\n"
							+ "[1] Approve Selective Course Registrations.\n"
							+ "[2] Approve All Course Registrations.\n"
							+ "[3] Reject Selective Course Registrations.\n"
							+ "[4] Reject All Pending Course Registrations.\n"
							+ "[5] Exit\n"
							+ "Enter your choice: ");
					
					while(!input.hasNextInt());
					choice = input.nextInt();
					
					switch(choice) {
					case 1:
						System.out.println("Enter Course Registration Ids to Approve: ");
						courseRegistrationIds.clear();
						
						while (true) {
			            	
							while(!input.hasNextInt());
							
			                courseRegistrationIds.add(input.nextInt());

			                do {
			                	System.out.print("Continue ? (Y/N): ");
			                	str = input.next();
			                } while(!str.matches("[YN|yn]"));
			                
			                if(str.matches("[N|n]")) {
			                	break;
			                }
			            }
						
						response = courseRegistrationOperation
							.updateCourseRegistrationStatusToByRegistrationIds(
								RegistrationStatus.APPROVED,
								courseRegistrationIds);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("Course Registration(s) have been " 
									+ RegistrationStatus.APPROVED.toString() + ".");
						} else {
							System.out.println("Request Failure");
						}
						
						break;
						
					case 2:
						response = courseRegistrationOperation
							.updateAllPendingCourseRegistrationsTo(
								RegistrationStatus.APPROVED);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("All Pending Course Registration(s) have been " 
									+ RegistrationStatus.APPROVED.toString() + ".");
						} else {
							System.out.println("Request Failure");
						}
						
						break;
						
					case 3:
						
						System.out.println("Enter Course Registration Ids to Reject: ");
						courseRegistrationIds.clear();
						
						while (true) {
			            	
							while(!input.hasNextInt());
							
			                courseRegistrationIds.add(input.nextInt());

			                do {
			                	System.out.print("Continue ? (Y/N): ");
			                	str = input.next();
			                } while(!str.matches("[YN|yn]"));
			                
			                if(str.matches("[N|n]")) {
			                	break;
			                }
			            }
						
						response = courseRegistrationOperation
							.updateCourseRegistrationStatusToByRegistrationIds(
								RegistrationStatus.REJECTED,	
								courseRegistrationIds);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("Course Registration(s) have been " + RegistrationStatus.REJECTED.toString() + ".");
						} else {
							System.out.println("Request Failure");
						}
						
						break;
						
					case 4:
						response = courseRegistrationOperation
							.updateAllPendingCourseRegistrationsTo(
								RegistrationStatus.REJECTED);
						
						if(Boolean.TRUE.equals(response)) {
							System.out.println("All Pending Course Registration(s) have been " + RegistrationStatus.REJECTED.toString() + ".");
						} else {
							System.out.println("Request Failure");
						}
						
						break;
						
					case 5:
						exit = true;
						break;
						
					default:
						System.out.println("Invalid choice, Please try again.");
					}
					
				}
				
				break;
				
			case 4:
				courseOperation.viewAllCourses();
				break;
				
			case 5:
				System.out.println("--- Add a new Course to catalog:- ---");
				System.out.print("Enter Title of the Course: ");
				String courseTitle = input.next();
				System.out.print("Enter Year of the Course: ");
				while(!input.hasNextInt());
				Integer courseYear = input.nextInt();
				System.out.print("Enter Semester of the Course: ");
				while(!input.hasNextInt());
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
				
				courseOperation.addCourse(course);
				
				break;
				
			case 6:
				System.out.println("--- Drop Course from catalog:- ---");

				System.out.print("Enter Course Id: ");
				courseId = input.nextInt();
				
				courseOperation.removeCourseById(courseId);
				
				break;
				
			case 7:
				System.out.println("--- Assign course to professor:- ---");

				System.out.print("Enter Course Id: ");
				courseId = input.nextInt();
				System.out.println("Enter Professor Id: ");
				Integer professorId = input.nextInt();
				
				courseOperation.assignCourseToProfessor(
						courseId, professorId);
				
				break;
			
//			case 7:
//				System.out.println("Missing Functionality.");
//				break;
				
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
		
		System.out.println("User Log Out.");
		return Boolean.FALSE;
	}
	
}
