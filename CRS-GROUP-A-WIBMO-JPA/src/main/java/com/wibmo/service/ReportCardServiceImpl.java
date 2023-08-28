/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.repository.*;
import com.wibmo.entity.Course;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseNotExistsInCatalogException;

/**
 * 
 */

public class ReportCardServiceImpl implements ReportCardService {

	@Autowired
	private CourseServiceImpl courseOperation;

	private ReportCardRepository reportCardRepository;
	
	@Override
	public void viewReportCardByStudent(Student student) {
		
		// TODO: Map should be a TreeMap
		Map<Integer, ArrayList<ReportCard>> semesterToReportCardMap = getSemesterToReportCardMapByStudentId(student.getStudentId());
		
		Set<Integer> courseIds = new HashSet<>();
		
		semesterToReportCardMap
			.entrySet()
			.stream()
			.map(entry -> entry.getValue())
			.forEach(semWiseReportCards -> {
				semWiseReportCards
					.forEach(reportCard -> {
						courseIds.add(reportCard.getCourseId());
					});
			});
		
		Map<Integer, Course> courseIdToCourseMap = courseOperation.getCourseIdToCourseMap(courseIds);
		
		System.out.println("**** Student Report Card:- ****\n");
		semesterToReportCardMap
			.entrySet()
			.stream()
			.forEach(entry -> {
				System.out.print(
						  "\n+---------------+\n"
						+ "Semester = " + entry.getKey() 
						+ "\n+---------------+\n");
				
				System.out.println(" CourseId |\tCourseTitle\t| CourseType\t| Dept.\t| Grade\n"
						+ "+------------------------------------------------------------------+");
				
				entry
					.getValue()
					.forEach(reportCard -> {
						System.out.format("    %d\t| %s\t| %s\t| %s\t| %s\n", 
							reportCard.getCourseId(),
							courseIdToCourseMap.get(
								reportCard.getCourseId()).getCourseTitle(), // C Programming Lang.
							courseIdToCourseMap.get(
								reportCard.getCourseId()).getCourseType(),	// PRIMARY						
							courseIdToCourseMap.get(
								reportCard.getCourseId()).getDepartment(),  // CSE, ECE 									
							reportCard.getGrade());						// "A"
					});
				
				System.out.println("+------------------------------------------------------------------+\n");
			});
	}

	@Override
	public void uploadReportCards(List<ReportCard> reportCards) {
		
		if(null == reportCards || reportCards.isEmpty()) {
			return;
		}
		
		// TODO: Should use Batch-insert functionality
		reportCards
			.forEach(reportCard -> {
				if(hasEntry(reportCard)) {
					reportCardRepository.update(reportCard); //particular gradeID in DB.
				} else {
					reportCardRepository.save(reportCard);
				}
			});
		
		System.out.println("Grade Upload Success.");
	}
	
	@Override
	public Map<Integer, ArrayList<ReportCard>> getSemesterToReportCardMapByStudentId(Integer studentId) { //ArrayList of grades
		return reportCardRepository.findAllByStudentId(studentId);

	}
	
	@Override
	public ReportCard getReportCardByStudentForCourse(Student student, Integer courseId) {
		return reportCardRepository.findByStudentAndCourseId(student, courseId);
	}
	
	
	/**************************** Utility Methods *****************************/
	
	private boolean hasEntry(ReportCard reportCard) {
		return null != reportCard.getReportId();
	}

}
