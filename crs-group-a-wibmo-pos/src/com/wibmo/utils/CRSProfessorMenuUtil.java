/**
 * 
 */
package com.wibmo.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.Professor;
import com.wibmo.bean.ReportCard;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.ReportCardOperation;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotExistsInSystemException;

/**
 * Utility class to support Interactive UI for Professor.
 * 
 * @author abhishek.sharma
 */
public class CRSProfessorMenuUtil {

	public static List<ReportCard> viewReportCardEntryMenu(
			
			final Scanner scanner, 
			final Integer courseId,
			final Professor professor,
			final CourseOperation courseOperation,
			final ReportCardOperation reportCardOperation,
			final CourseRegistrationOperation courseRegistrationOperation) 
				throws 
					CourseNotExistsInCatalogException, 
					ProfessorNotExistsInSystemException, 
					ProfessorNotAssignedForCourseException {
		
		if(!courseOperation.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!courseOperation.isProfessorAssignedForCourse(professor.getProfessorId(), courseId)) {
			throw new ProfessorNotAssignedForCourseException(professor.getProfessorId(), courseId);
		}
		
		List<ReportCard> reportCards = new ArrayList<>();
		
		System.out.print(
				"\n+--------------------------------------------------------+\n"
				+ " StudentId | StudentName | CurrentGrade | Enter New Grade \n"
				+ "+--------------------------------------------------------+\n");
		
		// loop over each student one by one and 
		// ask the user to enter the grades.
		try {
			courseRegistrationOperation
				.getRegisteredStudentsByCourseId(courseId)
				.forEach(student -> {
					
					ReportCard reportCard = reportCardOperation
							.getReportCardByStudentForCourse(student, courseId);
					
					String newGrade;
					
					do {
						System.out.format("    %d   | %s\t|\t%s\t|\t", 
								student.getStudentId(), 
								student.getStudentName(),
								null != reportCard ? reportCard.getGrade() : "-");
						
						newGrade = scanner.next();
						
					} while(!newGrade.matches("[ABCDEf|abcdef]"));
					
					if(null != reportCard) {
						reportCard.setGrade(newGrade);
					} else {
						reportCard = new ReportCard();
						reportCard.setStudentId(student.getStudentId());
						reportCard.setCourseId(courseId);
						reportCard.setGrade(newGrade);
						reportCard.setSemester(student.getCurrentSemester());
						// TODO: Use LocalDate.now().getYear()
						reportCard.setYear(2021);
					}
					
					reportCards.add(reportCard);
				});
		} catch (CourseNotExistsInCatalogException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return reportCards;
	}
}
