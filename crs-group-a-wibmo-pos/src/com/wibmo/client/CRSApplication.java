package com.wibmo.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.UserOperation;
import com.wibmo.business.UserOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.business.AdminOperation;
import com.wibmo.business.AdminOperationImpl;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

/**
 * 
 */
public class CRSApplication {
	
	private static final Logger LOG = Logger.getLogger(CRSApplication.class);
		
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		String userName, userEmail, userPassword, confirmPassword;
		boolean exit = false;
		int choice;
		
		UserOperation userOperation = new UserOperationImpl();
		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		AdminOperation adminOperation = new AdminOperationImpl();
		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		User user;
		
		LOG.info("Welcome to Course Registration System\n"
				+ "Time: " + LocalTime.now().toString() + "\n"
				+ "Date: " + LocalDate.now().toString());
		
		while(!exit) {
			
			System.out.print(
				"+-----------------------------------------+\n"
				+ "[1] Login\n"
				+ "[2] Registration\n"
				+ "[3] Forgot Password\n"
				+ "[4] Exit\n"
				+ "Enter your choice: ");
		
			while(!input.hasNextInt());
			
			choice = input.nextInt();
			
			switch(choice) {
				
			case 1:
				// TODO: Should add Validator for type
				System.out.print("Enter User Email: ");
				userEmail = input.next();
				System.out.print("Enter Password: ");
				userPassword = input.next();
				
				user = authenticationService
							.login(userEmail, userPassword);

				if(user == null) {
					System.out.println("Login Failure");
					break;
				}
				
				if(RegistrationStatus
						.INVALID_REGISTRATION_STATUSES
						.contains(user.getRegistrationStatus())) {
					System.out.println("Login NOT Allowed. Your registration status is : " + user.getRegistrationStatus());
					break;
				}
				
				// NotificationOperation notificationOperation = new NotificationOperationImpl();
				// notificationOperation.retrieveMessages(user.getUserId()).forEach(notification -> System.out.println(notification.getNotificationMessage()));;
				switch(user.getUserType()) {
				
				case STUDENT:
					while(CRSStudentMenu.display(input, user));
					break;
				
				case PROFESSOR:
					while(CRSProfessorMenu.display(input, user));
					break;
					
				case ADMIN:
					while(CRSAdminMenu.display(input, user));
					break;
				}
				
				break;

			case 2:
				System.out.print("Enter your Name: " );
				userName = input.next();
				// TODO: Validator should validate email
				System.out.print("Enter your Email: ");
				userEmail = input.next();
				// TODO: Validator should check for strong password using regex
				// TODO: Validator should check if passwords match
				while(true) {
					System.out.print("Create a Password: ");
					userPassword = input.next();
					System.out.print("Confirm Password: ");
					confirmPassword = input.next();
					if(userPassword.equals(confirmPassword)) {
						break;
					}
					System.out.println("Passwords do not match.");
				}
				
				System.out.print("Choose User Type:\n"
						+ "[1] Student\n"
						+ "[2] Professor\n"
						+ "[3] Admin\n"
						+ "Enter your choice: ");
				while(!input.hasNextInt());
				choice = input.nextInt();
				
				user = new User();
				user.setUserEmail(userEmail);
				user.setPassword(userPassword);
				
				switch(choice) {
				case 1:
					user.setUserType(UserType.STUDENT);
					try {
						userOperation.add(user);
						
						studentOperation.add(
							new Student(
								userOperation.getUserIdByEmail(userEmail),
								userEmail,
								userName,
								1));
						
						System.out.println("Student Account Registration request sent to Admin for Approval.");
						
					} catch (UserWithEmailAlreadyExistsException e) {
						System.out.println(e.getMessage());
//						e.printStackTrace();
					}
					
					break;
					
				case 2:
					// TODO: Make Department an Enum
					String department = null;
					System.out.print("Choose Department:\n"
						+ "[1] CSE\n"
						+ "[2] ECE\n"
						+ "[3] ME\n"
						+ "Enter your choice: ");
					while(!input.hasNextInt());
					choice = input.nextInt();
					switch(choice) {
					case 1:
						department = "CSE";
						break;
					case 2:
						department = "ECE";
						break;
					case 3:
						department = "ME";
					}
					
					user.setUserType(UserType.PROFESSOR);
					try {
						userOperation.add(user);
						
						professorOperation.add(
							new Professor(
								userOperation.getUserIdByEmail(userEmail),
								userEmail,
								userName,
								department));
						
						System.out.println("Professor Account Registration request sent to Admin for Approval");
						
					} catch (UserWithEmailAlreadyExistsException e) {
						System.out.println(e.getMessage());
//						e.printStackTrace();
					}
					
					break;
					
				case 3:
					user.setUserType(UserType.ADMIN);
					try {
						userOperation.add(user);
						
						adminOperation.add(
							new Admin(
								userOperation.getUserIdByEmail(userEmail),
								userEmail,
								userName));
						
						System.out.println("Admin Account Registration sent to existing Admin for Approval.");
						
					} catch (UserWithEmailAlreadyExistsException e) {
						System.out.println(e.getMessage());
//						e.printStackTrace();
					}
					
				}
				break;
				
			case 3:
				System.out.println("Missing Functionality");
				break;
				
			case 4:
				exit = true;
				break;
				
			default:
				System.out.println("Invalid choice! Try again.");
				
			}
		}
		
		input.close();
		System.out.println("Good Bye!");
	}
	
}
