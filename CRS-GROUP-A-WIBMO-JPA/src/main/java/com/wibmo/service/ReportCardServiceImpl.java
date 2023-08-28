/**
 * 
 */
package com.wibmo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.dto.ReportCardResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.ReportCardRepository;

/**
 * 
 */
//@Service
//public class ReportCardServiceImpl implements ReportCardService {
//
//	@Autowired
//	private UserServiceImpl userService;
//	
//	@Autowired
//	private CourseServiceImpl courseService;
//	
//	@Autowired
//	private CourseRegistrationServiceImpl courseRegistrationService;
//	
//	@Autowired
//	private ReportCardRepository reportCardRepository;
//
//	@Override
//	public void addAll(List<ReportCardRequestDTO> reportCardRequestDTOs) {
//		/*
//		if(null == reportCardRequestDTOs) {
//			return;
//		}
//		
//		// TODO: Add Input values validations
//		
//		Map<Integer, Course> courseIdToCourseMap = courseService
//				.getCourseIdToCourseMap(
//						reportCardRequestDTOs
//							.stream()
//							.map(reportCardRequestDTO -> reportCardRequestDTO.getCourseId())
//							.filter(Objects::nonNull)
//							.collect(Collectors.toSet()));
//		
//		List<ReportCard> reportCards = new ArrayList<>();
//		
//		reportCardRequestDTOs
//			.stream()
//			.forEach(reportCardRequestDTO -> {
//				ReportCard reportCard = reportCardRepository
//						.findByStudentIdAndCourseId(
//								reportCardRequestDTO.getStudentId(),
//								reportCardRequestDTO.getCourseId());
//				if(null != reportCard) {
//					reportCard.setGrade(reportCardRequestDTO.getGrade());
//					reportCards.add(reportCard);
//				} else {
//					// TODO: Use a Converter
//					reportCard = new ReportCard();
//					reportCard.setCourseId(reportCardRequestDTO.getCourseId());
//					reportCard.setStudentId(reportCardRequestDTO.getStudentId());
//					reportCard.setGrade(reportCardRequestDTO.getGrade());
//					reportCard.setSemester(
//							courseIdToCourseMap
//								.get(reportCardRequestDTO.getCourseId())
//								.getSemester());
//					reportCard.setYear(LocalDate.now().getYear());
//					reportCards.add(reportCard);
//				}
//			});
//		
//		reportCardRepository.saveAll(reportCards);
//		*/
//	}
//	
//	@Override
//	public Map<Integer, List<ReportCardResponseDTO>> getSemesterToReportCardMapByStudentId(Integer studentId)
//		throws UserNotFoundException {
//		
//		if(!userService.isUserExistsById(studentId)) {
//			throw new UserNotFoundException(studentId);
//		}
//		
//		List<ReportCard> reportCards = reportCardRepository.findAllByStudentId(studentId);
//		
//		// We will sort the Map by semester
//		Map<Integer, List<ReportCardResponseDTO>> semesterToReportCardDTOMap = new TreeMap<>();
//		
//		Map<Integer, Course> courseIdToCourseMap = courseService
//				.getCourseIdToCourseMap(
//						reportCards
//							.stream()
//							.map(reportCard -> reportCard.getCourseId())
//							.collect(Collectors.toSet()));
//		
//		reportCards
//			.forEach(reportCard -> {
//				int semester = reportCard.getSemester();
//				if(!semesterToReportCardDTOMap.containsKey(semester)) {
//					semesterToReportCardDTOMap.put(semester, new ArrayList<>());
//				}
//				semesterToReportCardDTOMap
//					.get(semester)
//					.add(new ReportCardResponseDTO(
//							reportCard.getCourseId(),
//							courseIdToCourseMap.get(
//									reportCard.getCourseId()).getCourseTitle(),
//							reportCard.getGrade()));
//			});
//		
//		return semesterToReportCardDTOMap;
//	}
//	
//	@Override
//	public String getGradeByStudentIdAndCourseId(Integer studentId, Integer courseId)
//		throws 
//			UserNotFoundException, 
//			CourseNotExistsInCatalogException, 
//			StudentNotRegisteredForCourseException {
//		
//		if(!userService.isUserExistsById(studentId)) {
//			throw new UserNotFoundException(studentId);
//		}
//		
//		if(!courseService.isCourseExistsInCatalog(courseId)) {
//			throw new CourseNotExistsInCatalogException(courseId);
//		}
//		
//		if(!courseRegistrationService.hasRegistrationByStudentIdAndCourseId(studentId, courseId)) {
//			throw new StudentNotRegisteredForCourseException(studentId, courseId);
//		}
//		
//		return reportCardRepository
//				.findByStudentIdAndCourseId(studentId, courseId) 
//				.getGrade();
//	}
//
//	@Override
//	public List<ReportCardResponseDTO> getReportCardByStudentIdAndSemester(Integer studentId, Integer semester) 
//			throws 
//			UserNotFoundException, 
//			StudentNotRegisteredForSemesterException {
//		
//		if(!userService.isUserExistsById(studentId)) {
//			throw new UserNotFoundException(studentId);
//		}
//		
//		if(!courseRegistrationService
//				.hasRegistrationByStudentIdAndSemester(
//						studentId, semester)) {
//			throw new StudentNotRegisteredForSemesterException(studentId, semester);
//		}
//		
//		List<ReportCard> reportCards = reportCardRepository.findByStudentIdAndSemester(studentId, semester);
//		
//		Map<Integer, Course> courseIdToCourseMap = courseService
//				.getCourseIdToCourseMap(
//						reportCards
//							.stream()
//							.map(reportCard -> reportCard.getCourseId())
//							.collect(Collectors.toSet()));
//		
//		return reportCards
//				.stream()
//				.map(reportCard -> new ReportCardResponseDTO(
//						reportCard.getCourseId(),
//						courseIdToCourseMap.get(reportCard.getCourseId()).getCourseTitle(),
//						reportCard.getGrade()))
//				.collect(Collectors.toList());
//	}
//	
//	@Override
//	public ReportCardResponseDTO getReportCardByStudentForCourse(Student student, Integer courseId) {
//		ReportCard reportCard = reportCardRepository.findByStudentAndCourseId(student, courseId);
//		Course course = courseService.getCourseIdToCourseMap(
//							Set.of(courseId))
//						.get(courseId);
//		return new ReportCardResponseDTO(
//				course.getCourseId(),
//				course.getCourseTitle(),
//				reportCard.getGrade());
//	}
//	
//
//}
