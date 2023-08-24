/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.service.ReportCardServiceImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class ReportCardServiceTest {

	@Autowired
	private ReportCardServiceImpl reportCardService;
	
	/**
	 * Junit test for viewReportCardByStudent
	 */
//	@Test
//	public void viewReportCardByStudentTest() {
//		
//	}
	
	/**
	 * Junit test for uploadReportCards
	 */
	@Test
	public void uploadReportCardsTest() {
		List<ReportCard> repCardList = new ArrayList<>();
		ReportCard repCard = new ReportCard(null, 1010, 11, "B", 1, 2021);
		repCardList.add(repCard);
		Student testStudent = new Student(1010, "mohit@stu.user.com", "Mohit", 1);
		reportCardService.uploadReportCards(repCardList);
		
		ReportCard actualReportCard = reportCardService.getReportCardByStudentForCourse(testStudent, 11);
		
		assertEquals(repCard.getCourseId(), actualReportCard.getCourseId());
		assertEquals(repCard.getStudentId(), actualReportCard.getStudentId());
	}
	
	/**
	 * Junit test for getSemesterToReportCardMapByStudentId
	 */
	//error brackets missing
	@Test
	public void getSemesterToReportCardMapByStudentId() {
		Map<Integer, ArrayList<ReportCard>> expectedMap = new HashMap<>();
		ArrayList<ReportCard> reportList = new ArrayList<>();
		ReportCard repCard = new ReportCard(9, 1001, 16, "B", 1, 2021);
		reportList.add(repCard);
		repCard = new ReportCard(10, 1001, 2, "A", 1, 2021);
		reportList.add(repCard);
		repCard = new ReportCard(11, 1001, 8, "B", 1, 2021);
		reportList.add(repCard);
		expectedMap.put(1, reportList);
		
		Map<Integer, ArrayList<ReportCard>> actualMap = reportCardService.getSemesterToReportCardMapByStudentId(1001);
		
		assertEquals(expectedMap, actualMap);
	}
	
	/**
	 * Junit test for getReportCardByStudentForCourse
	 */
	//fetching correct report card but some error
	@Test
	public void getReportCardByStudentForCourse() {
		Student testStudent = new Student(1001, "abhi@stu.user.com", "Abhishek", 1);
		ReportCard expectedReportCard = new ReportCard(9, 1001, 16, "B", 1, 2021);
		
		ReportCard actualReportCard = reportCardService.getReportCardByStudentForCourse(testStudent, 16);
		
		assertEquals(expectedReportCard, actualReportCard);
	}

}
