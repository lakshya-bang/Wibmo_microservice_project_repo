package com.wibmo.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
public class CRSApplication {
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		boolean exit = false;
		int choice;
		
		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		
		System.out.println("Welcome to Course Registration System\n"
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
				
				User user = authenticationService.login();
				
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
				System.out.println("Missing Functionality");
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
