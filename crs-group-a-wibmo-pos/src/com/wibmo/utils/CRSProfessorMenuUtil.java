/**
 * 
 */
package com.wibmo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.ReportCard;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.exception.CourseNotExistsInCatalogException;

/**
 * Utility class to support Interactive UI for Professor.
 * 
 * @author abhishek.sharma
 */
public class CRSProfessorMenuUtil {

	public static List<ReportCard> viewReportCardEntryMenu(
			final Scanner scanner, 
			final Integer courseId,
			final Integer professorId,
			final CourseOperation courseOperation,
			final CourseRegistrationOperation courseRegistrationOperation) 
					throws CourseNotExistsInCatalogException {
		
		if(!courseOperation.isCourseExistsInCatalogue(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!courseOperation.isProfessorAssignedForCourse(professorId, courseId)) {
			throw new ProfessorNotAssignedForCourseException(professorId, courseId);
		}
		
		List<ReportCard> reportCards = new ArrayList<>();
		
		// loop over each student one by one and 
		// ask the user to enter the grades.
		try {
			courseRegistrationOperation
				.getRegisteredStudentsByCourseId(courseId)
				.forEach(student -> {
					String grade = null;
					
					do {
						System.out.print("StudentId: " + student.getStudentId() + ", StudentName: " + student.getStudentName() + ", Enter Grade: ");
						grade = scanner.next();
					} while(!grade.matches("[ABCDEf|abcdef]"));
					
					ReportCard reportCard = new ReportCard();
					reportCard.setStudentId(student.getStudentId());
					reportCard.setCourseId(courseId);
					reportCard.setGrade(grade);
					reportCard.setSemester(student.getCurrentSemester());
					// TODO: Fix Year
					reportCard.setYear(2021);
					
					reportCards.add(reportCard);
				});
		} catch (CourseNotExistsInCatalogException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return reportCards;
	}
}
