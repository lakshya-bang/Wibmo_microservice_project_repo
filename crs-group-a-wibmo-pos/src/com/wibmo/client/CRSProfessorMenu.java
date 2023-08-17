/**
 * 
 */
package com.wibmo.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.wibmo.bean.Professor;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.User;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ProfessorBusiness;
import com.wibmo.business.ProfessorBusinessImpl;
import com.wibmo.business.ReportCardOperation;
import com.wibmo.business.ReportCardOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;

/**
 * 
 */
public class CRSProfessorMenu {
	
	public static Boolean display(User user) {
		
		Scanner input = new Scanner(System.in);
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
		ReportCardOperation gradeOperation = new ReportCardOperationImpl(courseOperation);
		
		Professor professor = professorOperation.getProfessorById(user.getUserId());
		
		System.out.print("+......... Welcome, " + professor.getProfessorName() +  ".........+\n");
		System.out.println("+------------------------------------+");
		while(!logout) {
			System.out.println( "[0] View Courses Taught\n"
					+ "[1] View Registered Students\n"
					+ "[2] Upload Grades\n"
					+ "[3] Logout\n"
					+ "Enter your choice: ");
			
			choice = input.nextInt();
			
			switch(choice) {
			
			case 0:
				System.out.println("*** List of Courses Taught:- ***\n");
				System.out.println(" CourseId    CourseTitle    CourseType ");
				System.out.println("+------------------------------------+");
				courseOperation
					.getCoursesAssignedToProfessor(professor.getProfessorId())
					.forEach(course -> System.out.format(
							"%5d%15s%15s\n", 
								course.getCourseId(), 
								course.getCourseTitle(),
								course.getCourseType().toString()));
				System.out.println("+------------------------------------+\n");
				break;
			
			case 1:
				System.out.println("Enter the courseId to view registered students: ");
				// TODO: Should check if this professor is teaching this course
				courseId = input.nextInt();
				System.out.println("*** List of Registered Students:- ***\n");
				System.out.println(" StudentId    StudentName ");
				System.out.println("+------------------------+");
				courseRegistrationOperation
					.getRegisteredStudentsByCourseId(courseId)
					.forEach(student -> System.out.format(
							"%5s%20s\n", 
								student.getStudentId(), 
								student.getStudentName()));
				System.out.println("+------------------------+\n");
				break;
				
			case 2:
				System.out.println("Enter the course ID for which you want to upload the Grades- ");
				courseId = input.nextInt();
				ProfessorBusiness professorBusiness = new ProfessorBusinessImpl();
				// TODO: if wrong course selected, i.e.
				// this professor does not teaches any of his assigned courses
				// show message, and break
				
				Map<Integer, String> studentIdToAssignedGradeMap = new HashMap<>();
				
				// loop over each student one by one and ask the user to enter the grades.
				courseRegistrationOperation
					.getRegisteredStudentsByCourseId(courseId)
					.forEach(student -> {
						String grade = null;
						do {
							System.out.print("StudentId: " + student.getStudentId() + ", StudentName: " + student.getStudentName() + ", Enter Grade: ");
							grade = input.next();
						} while(!grade.matches("[ABCDEf|abcdef]"));
						studentIdToAssignedGradeMap.put(student.getStudentId(), grade);
					});
				for(Map.Entry<Integer,String> entry : studentIdToAssignedGradeMap.entrySet()){
					ReportCard reportCard = new ReportCard();
					ReportCardOperation reportCardOperation = new ReportCardOperationImpl(courseOperation);
					reportCard.setStudentId(entry.getKey());
					System.out.println("Please enter the semester: ");
					Integer semester = input.nextInt();
					reportCard.setSemester(semester);
					System.out.println("Please enter the year: ");
					Integer year = input.nextInt();
					reportCard.setYear(year);
					reportCard.setCourseId(courseId);
					reportCard.setGrade(entry.getValue());
					reportCardOperation.uploadGrades(reportCard);
				}
				break;
				
			case 3:
				logout = true;
				break;
				
			default:
				System.out.println("Invalid choice! Try again.");
				
			}
		}
		//input.close(); Because of the extra closing of the input.

		return Boolean.FALSE;
	}
}
