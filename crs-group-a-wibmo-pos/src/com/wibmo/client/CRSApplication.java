package com.wibmo.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Student;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.CourseCatalogueOperation;
import com.wibmo.business.CourseCatalogueOperationImpl;
import com.wibmo.business.FakeAuthenticationService;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;

/**
 * 
 */
public class CRSApplication {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		Long userId, courseId;
		String password;
		boolean exit = false;
		boolean response;
		int ch;
		
		AuthenticationService authenticationService = new FakeAuthenticationService();
		
		System.out.println("Welcome to Course Registration System\n"
				+ "Time: " + LocalTime.now().toString() + "\n"
				+ "Date: " + LocalDate.now().toString());
		
		while(!exit) {
			
			System.out.print(
				"+-----------------------------------------+"
				+ "[1] Login\n"
				+ "[2] Registration\n"
				+ "[3] Forgot Password\n"
				+ "[4] Exit\n"
				+ "Enter your choice: ");
		
			ch = in.nextInt();
		
			switch(ch) {
				
			case 1:
				userId = in.nextLong();
				password = in.next();
				
				response = authenticationService.login(userId, password);
				
				if(!response) {
					System.out.println("Login Failure");
					break;
				}
				
				while(crsStudentMenu.display());
				
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
	}
	
}
