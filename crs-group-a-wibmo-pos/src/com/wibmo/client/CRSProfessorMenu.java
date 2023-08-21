/**
 * 
 */
package com.wibmo.client;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.bean.Professor;
import com.wibmo.bean.User;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ReportCardOperation;
import com.wibmo.business.ReportCardOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotExistsInSystemException;
import com.wibmo.utils.CRSProfessorMenuUtil;
import com.wibmo.utils.ProfessorNotAssignedForCourseException;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;

/**
 * 
 */
public class CRSProfessorMenu {
	
	// Plug Logger in CRSStudentMenu logger injection
	private static final Logger LOG = Logger.getLogger(CRSProfessorMenu.class);	
	
	public static Boolean display(Scanner input, User user) {
		
		Integer courseId;
		boolean logout = false;
		int choice;
		
		StudentOperation studentOperation = new StudentOperationImpl();
		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				professorOperation);
		CourseRegistrationOperation courseRegistrationOperation =
				new CourseRegistrationOperationImpl(
						studentOperation, professorOperation, courseOperation);
		ReportCardOperation reportCardOperation = new ReportCardOperationImpl(
				studentOperation, courseOperation, courseRegistrationOperation);
		
		Professor professor = professorOperation.getProfessorById(user.getUserId());
		
		LOG.info("+......... Welcome Professor .........+\n"
				+ "Professor Id : " + professor.getProfessorId() + "\n"
				+ "Professor Name : " + professor.getProfessorName() + "\n"
				+ "Professor Email : " + professor.getProfessorEmail());
		
		while(!logout) {
			System.out.print("\n+-----------------------------------+\n"
					+ "[0] View Courses Taught\n"
					+ "[1] View Registered Students\n"
					+ "[2] Upload Grades\n"
					+ "[3] Logout\n"
					+ "Enter your choice: ");
			
			choice = input.nextInt();
			
			switch(choice) {
			
			case 0:
				courseOperation.viewCoursesTaughtByProfessor(professor);
				break;
			
			case 1:
				System.out.print("Enter the Course Id: ");
				courseId = input.nextInt();
				
				try {
					courseRegistrationOperation
						.viewRegisteredStudentsByProfessorIdAndCourseId(
							professor.getProfessorId(), courseId);
				} catch (CourseNotExistsInCatalogException 
						| ProfessorNotExistsInSystemException
						| ProfessorNotAssignedForCourseException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				break;
				
			case 2:
				System.out.print("Enter the Course Id: ");
				courseId = input.nextInt();
				
				try {
					reportCardOperation.uploadReportCards(
						CRSProfessorMenuUtil
							.viewReportCardEntryMenu(
								input, 
								courseId, 
								professor, 
								courseOperation, 
								reportCardOperation,
								courseRegistrationOperation));
					
				} catch (CourseNotExistsInCatalogException 
						| ProfessorNotExistsInSystemException 
						| ProfessorNotAssignedForCourseException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
				}
				
				break;
				
			case 3:
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
