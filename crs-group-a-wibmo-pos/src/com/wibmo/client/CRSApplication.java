package com.wibmo.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.AuthenticationServiceImpl;

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
		
			choice = input.nextInt();
		
			switch(choice) {
				
			case 1:
				
				User user = authenticationService.login();
				
				if(user == null) {
					System.out.println("Login Failure");
					break;
				}
				
				switch(user.getUserType()) {
				
				case STUDENT:
					while(CRSStudentMenu.display(user));
					break;
				
				case PROFESSOR:
					while(CRSProfessorMenu.display(user));
					break;
					
				case ADMIN:
					while(CRSAdminMenu.display(user));
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
				
			default:
				System.out.println("Invalid choice! Try again.");
				
			}
		}
		
		input.close();
	}
	
}
