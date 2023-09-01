/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.converter.ReportCardConverter;
import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.dto.ReportCardResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.ReportCard;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CannotAddGradeStudentRegistrationNotApprovedException;
import com.wibmo.exception.CourseIdCannotBeEmptyException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.GradeCannotBeEmptyException;
import com.wibmo.exception.GradeValueInvalidException;
import com.wibmo.exception.StudentIdCannotBeEmptyException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.ReportCardRepository;

/**
 * 
 */

public class ReportCardServiceImpl implements ReportCardService {
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private CourseServiceImpl courseService;
	
	@Autowired
	private CourseRegistrationServiceImpl courseRegistrationService;
	
	@Autowired
	private ReportCardConverter reportCardConverter;
	
	@Autowired
	private ReportCardRepository reportCardRepository;

	@Override
	public void addAll(List<ReportCardRequestDTO> reportCardRequestDTOs)
			throws
				StudentNotRegisteredForCourseException,
				CannotAddGradeStudentRegistrationNotApprovedException, 
				UserNotFoundException, 
				CourseNotExistsInCatalogException,
				StudentIdCannotBeEmptyException,
				CourseIdCannotBeEmptyException,
				GradeCannotBeEmptyException,
				GradeValueInvalidException {
		
		if(null == reportCardRequestDTOs) {
			return;
		}
		
		/*************** Input Values Validations *****************/
		
		for(ReportCardRequestDTO reportCardRequestDTO : reportCardRequestDTOs) {
			Integer studentId = reportCardRequestDTO.getStudentId();
			Integer courseId = reportCardRequestDTO.getCourseId();
			String grade = reportCardRequestDTO.getGrade();
			if(studentId == null) {
				throw new StudentIdCannotBeEmptyException();
			}
			if(courseId == null) {
				throw new CourseIdCannotBeEmptyException();
			}
			if(grade == null) {
				throw new GradeCannotBeEmptyException();
			}
			if(!grade.matches("[abcdef|ABCDEF]")) {
				throw new GradeValueInvalidException();
			}
			if(!studentService.isStudentExistsById(studentId)) {
				throw new UserNotFoundException(studentId, UserType.STUDENT);
			}
			if(!courseService.isCourseExistsInCatalog(courseId)) {
				throw new CourseNotExistsInCatalogException(courseId);
			}
		}
		
		/***********************************************************************/
		
		// TODO: Should enhance via Join Query
		for(ReportCardRequestDTO reportCard : reportCardRequestDTOs) {
			
			Integer studentId = reportCard.getStudentId();
			Integer courseId = reportCard.getCourseId();
			Integer semester = studentService.getCurrentSemesterByStudentId(studentId);
			CourseRegistration courseRegistration = courseRegistrationService
					.getCourseRegistrationByStudentIdAndSemester(studentId, semester);
			
			if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(courseRegistration.getRegistrationStatus())) {
				throw new CannotAddGradeStudentRegistrationNotApprovedException(
						studentId, courseRegistration.getRegistrationStatus());
			}
		
			if(!courseRegistrationService.hasRegistrationByStudentIdAndCourseId(studentId, courseId)) {
				throw new StudentNotRegisteredForCourseException(studentId, courseId);
			}
		}
		
		/***************************************************************************************/
		
		Map<Integer, Course> courseIdToCourseMap = courseService
				.getCourseIdToCourseMap(
						reportCardRequestDTOs
							.stream()
							.map(reportCardRequestDTO -> reportCardRequestDTO.getCourseId())
							.filter(Objects::nonNull)
							.collect(Collectors.toSet()));
		
		List<ReportCard> reportCards = new ArrayList<>();
		
		reportCardRequestDTOs
			.stream()
			.forEach(reportCardRequestDTO -> {
				Optional<ReportCard> reportCardOptional = reportCardRepository
						.findByStudentIdAndCourseId(
								reportCardRequestDTO.getStudentId(),
								reportCardRequestDTO.getCourseId());
				if(reportCardOptional.isPresent()) {
					ReportCard reportCard = reportCardOptional.get();
					reportCard.setGrade(reportCardRequestDTO.getGrade());
					reportCards.add(reportCard);
				} else {
					reportCards.add(
							reportCardConverter.convert(
									reportCardRequestDTO,
									courseIdToCourseMap));
				}
			});
		
		reportCardRepository.saveAll(reportCards);
		
	}
	
	@Override
	public Map<Integer, List<ReportCardResponseDTO>> getSemesterToReportCardMapByStudentId(Integer studentId)
		throws UserNotFoundException {
		
		if(!studentService.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		List<ReportCard> reportCards = reportCardRepository.findAllByStudentId(studentId);
		
		// We will sort the Map by semester
		Map<Integer, List<ReportCardResponseDTO>> semesterToReportCardDTOMap = new TreeMap<>();
		
		Map<Integer, Course> courseIdToCourseMap = courseService
				.getCourseIdToCourseMap(
						reportCards
							.stream()
							.map(reportCard -> reportCard.getCourseId())
							.collect(Collectors.toSet()));
		
		reportCards
			.forEach(reportCard -> {
				int semester = reportCard.getSemester();
				if(!semesterToReportCardDTOMap.containsKey(semester)) {
					semesterToReportCardDTOMap.put(semester, new ArrayList<>());
				}
				semesterToReportCardDTOMap
					.get(semester)
					.add(new ReportCardResponseDTO(
							reportCard.getCourseId(),
							courseIdToCourseMap.get(
									reportCard.getCourseId()).getCourseTitle(),
							reportCard.getGrade()));
			});
		
		return semesterToReportCardDTOMap;
	}
	
	@Override
	public ReportCardResponseDTO getReportCardByStudentIdAndCourseId(Integer studentId, Integer courseId) 
			throws UserNotFoundException, CourseNotExistsInCatalogException, StudentNotRegisteredForCourseException {
		
		if(!studentService.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(!courseService.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!courseRegistrationService.hasRegistrationByStudentIdAndCourseId(studentId, courseId)) {
			throw new StudentNotRegisteredForCourseException(studentId, courseId);
		}
		
		Optional<ReportCard> reportCardOptional = reportCardRepository
				.findByStudentIdAndCourseId(studentId, courseId);
		
		Course course = courseService
				.getCourseIdToCourseMap(Set.of(courseId)).get(courseId);
		
		if(!reportCardOptional.isPresent()) {
			return null; // TODO: Should throw
		}
		
		return new ReportCardResponseDTO(
				course.getCourseId(),
				course.getCourseTitle(),
				reportCardOptional.get().getGrade());
		
	}
	
	@Override
	public String getGradeByStudentIdAndCourseId(Integer studentId, Integer courseId)
		throws 
			UserNotFoundException, 
			CourseNotExistsInCatalogException, 
			StudentNotRegisteredForCourseException {
		
		ReportCardResponseDTO reportCard = getReportCardByStudentIdAndCourseId(studentId, courseId);

		return reportCard == null ? "Nil" : reportCard.getGrade();
	}

	@Override
	public List<ReportCardResponseDTO> getReportCardByStudentIdAndSemester(Integer studentId, Integer semester) 
			throws 
			UserNotFoundException, 
			StudentNotRegisteredForSemesterException {
		
		if(!studentService.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(!courseRegistrationService
				.hasRegistrationByStudentIdAndSemester(
						studentId, semester)) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		List<ReportCard> reportCards = reportCardRepository.findAllByStudentIdAndSemester(studentId, semester);
		
		Map<Integer, Course> courseIdToCourseMap = courseService
				.getCourseIdToCourseMap(
						reportCards
							.stream()
							.map(reportCard -> reportCard.getCourseId())
							.collect(Collectors.toSet()));
		
		return reportCards
				.stream()
				.map(reportCard -> new ReportCardResponseDTO(
						reportCard.getCourseId(),
						courseIdToCourseMap.get(reportCard.getCourseId()).getCourseTitle(),
						reportCard.getGrade()))
				.collect(Collectors.toList());
	}
	

}
