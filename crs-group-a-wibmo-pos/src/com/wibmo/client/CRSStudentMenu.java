package com.wibmo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ReportCardOperation;
import com.wibmo.business.ReportCardOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;

public class CRSStudentMenu {

	public static Boolean display(User user) {

		Scanner input = new Scanner(System.in);
		Integer courseId;
		boolean logout = false;
		int choice;

		ProfessorOperation professorOperation = new ProfessorOperationImpl();
		CourseOperation courseOperation = new CourseOperationImpl(
				professorOperation);
		StudentOperation studentOperation = new StudentOperationImpl();
		CourseRegistrationOperation courseRegistrationOperation = 
				new CourseRegistrationOperationImpl(
						studentOperation, professorOperation, courseOperation);
		ReportCardOperation gradeOperation = new ReportCardOperationImpl(courseOperation);

		Student student = studentOperation.getStudentById(user.getUserId());

		System.out.print("+......... Welcome Student .........+\n");
		System.out.println(user);
		System.out.println(student);

		while (!logout) {
			System.out.println("+-------------------------+\n" 
					+ "[0] View Course Catalogue\n"
					+ "[1] Course Registration\n"
					+ "[2] View Registered Courses\n"
					+ "[3] View Registration Status\n"
					+ "[4] Add Course\n" 
					+ "[5] Drop Course\n" 
					+ "[6] View Grades\n"
					+ "[7] Pay Bill\n" 
					+ "[8] Logout\n" 
					+ "Enter your choice: ");

			choice = input.nextInt();

			switch (choice) {

			case 0:
				System.out.println("*** Course Catalogue:- ***");
				courseOperation.viewCourseDetailsBySemester(student.getCurrentSemester());
				
			case 1:
				// TODO: Admin should enable registration first
				
				List<Integer> primaryCourses = new ArrayList<>();
				List<Integer> alternativeCourses = new ArrayList<>();
				// 4 Primary Courses to be selected:
				System.out.println("Enter 4 Primary Course Ids: ");
				while(primaryCourses.size() != 4) {
					courseId = input.nextInt();
					if(primaryCourses.contains(courseId)) {
						System.out.println("Already selected, choose another.");
					} else {
						primaryCourses.add(courseId);
					}
				}
				System.out.println("Primary Courses Selected: " + primaryCourses);
				
				// 2 Alternative Courses to be selected:
				System.out.println("Enter 2 Alternative Course Ids: ");
				while(alternativeCourses.size() != 2) {
					courseId = input.nextInt();
					if(alternativeCourses.contains(courseId)) {
						System.out.println("Already selected, choose another.");
					} else {
						alternativeCourses.add(courseId);
					}
				}
				System.out.println("Alternative Courses Selected: " + alternativeCourses);
				
				courseRegistrationOperation.register(
						primaryCourses, 
						alternativeCourses,
						student);
				
//				try {
//					courseRegistrationOperation.register(
//							primaryCourses, 
//							alternativeCourses,
//							student);
//				} catch (StudentNotFoundException | CoursesNotAvailableForRegistrationException
//						| StudentAlreadyRegisteredForSemesterException e) {
//					System.out.println(e.getMessage());
////					e.printStackTrace();
//				}
				break;

			case 2:
				courseRegistrationOperation.viewRegisteredCoursesByStudent(student);
				break;

			case 3:
				courseRegistrationOperation.getRegistrationStatusByStudent(student);
				break;
				
			case 4:
				// TODO
//				System.out.println("*** Course Catalogue:- ***");
//				courseOperation.viewCourseDetailsBySemester(student.getCurrentSemester());
//				System.out.print("Enter Id of the course you wish to add: ");
//				courseId = in.nextInt();
//				
//				// TODO: Throw Exception: Add check if student is full for this semester, they cannot add any more course.
//				// TODO: Throw Exception: Add check so that student should not choose one of already registered courses.
//				courseRegistrationOperation.addCourse(courseId, student);
				
				break;

			case 5:
				// TODO
//				System.out.print("Enter Id of the course you wish to drop: ");
//				courseId = in.nextInt();
//				
//				// TODO: Throw Exception: Add check so that student should not choose a courseId that they have not even registered.
//				courseRegistrationOperation.dropCourse(courseId, student);
				
				break;

			case 6:
				gradeOperation.viewGradesByStudent(student);
				break;

			case 7:
				// TODO
//				billOperation.payBill(userId);
				break;

			case 8:
				logout = true;
				break;

			default:
				System.out.println("Invalid choice! Try again.");

			}
		}
		
		input.close();
		return Boolean.FALSE;
	}
}
