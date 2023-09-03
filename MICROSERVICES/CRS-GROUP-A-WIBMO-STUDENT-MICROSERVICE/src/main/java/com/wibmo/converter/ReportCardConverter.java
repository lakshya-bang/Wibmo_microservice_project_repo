package com.wibmo.converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.dto.ReportCardResponseDTO;
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
		// The semester field should be set based on the semester
		// in which the course was taught.
		reportCard.setSemester(
				courseIdToCourseMap
					.get(reportCardRequestDTO.getCourseId())
					.getSemester());
		reportCard.setYear(LocalDate.now().getYear());
		return reportCard;
	}
	
	public ReportCardResponseDTO convert(
			ReportCard reportCard,
			Map<Integer, Course> courseIdToCourseMap) {
		return new ReportCardResponseDTO(
				courseIdToCourseMap.get(
						reportCard.getCourseId()).getCourseId(),
				courseIdToCourseMap.get(
						reportCard.getCourseId()).getCourseTitle(),
				null != reportCard ? reportCard.getGrade() : "Nil");
	}
	
	public List<ReportCardResponseDTO> convertAll(
			List<ReportCard> reportCards,
			Map<Integer, Course> courseIdToCourseMap) {
		return reportCards
				.stream()
				.map(reportCard -> convert(reportCard, courseIdToCourseMap))
				.collect(Collectors.toList());
	}
	
}
