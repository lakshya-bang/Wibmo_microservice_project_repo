/**
 * 
 */
package com.wibmo.converter;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.ReportCard;

/**
 * 
 */
@Service
public class ReportCardConverter {

	public ReportCard convert(
			ReportCardRequestDTO reportCardRequestDTO,
			Map<Integer, Course> courseIdToCourseMap) {
		ReportCard reportCard = new ReportCard();
		reportCard.setCourseId(reportCardRequestDTO.getCourseId());
		reportCard.setStudentId(reportCardRequestDTO.getStudentId());
		reportCard.setGrade(reportCardRequestDTO.getGrade());
		reportCard.setSemester(
				courseIdToCourseMap
					.get(reportCardRequestDTO.getCourseId())
					.getSemester());
		reportCard.setYear(LocalDate.now().getYear());
		return reportCard;
	}
}
