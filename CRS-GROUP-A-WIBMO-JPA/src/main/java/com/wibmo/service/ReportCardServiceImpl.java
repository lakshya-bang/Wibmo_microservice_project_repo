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
import com.wibmo.entity.Student;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CannotAddGradeStudentPaymentPendingException;
import com.wibmo.exception.CannotAddGradeStudentRegistrationNotApprovedException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.ReportCardRepository;

/**
 * 
 */
@Service
public class ReportCardServiceImpl implements ReportCardService {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private CourseServiceImpl courseService;
	
	@Autowired
	private CourseRegistrationServiceImpl courseRegistrationService;
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private ReportCardConverter reportCardConverter;
	
	@Autowired
	private ReportCardRepository reportCardRepository;

	@Override
	public void addAll(List<ReportCardRequestDTO> reportCardRequestDTOs)
			throws 
				CannotAddGradeStudentPaymentPendingException, 
				StudentNotRegisteredForCourseException,
				CannotAddGradeStudentRegistrationNotApprovedException {
		
		if(null == reportCardRequestDTOs) {
			return;
		}
		
		// TODO: Add Input values validations
		
		/****** Check whether these students have paid for their course registrations ********/
		
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
			
			PaymentStatus paymentStatus = 
					paymentService.getPaymentStatusByCourseRegistrationId(
							courseRegistration.getRegistrationId());
			
			if(PaymentStatus.UNPAID.equals(paymentStatus)) {
				throw new CannotAddGradeStudentPaymentPendingException(studentId, semester);
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
		
		if(!userService.isUserExistsById(studentId)) {
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
		
		if(!userService.isUserExistsById(studentId)) {
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
		
		if(!userService.isUserExistsById(studentId)) {
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
