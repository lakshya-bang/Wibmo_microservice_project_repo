package com.wibmo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.exception.CannotApproveCourseRegistrationPaymentPendingException;
import com.wibmo.exception.CourseNotAvailableDueToSeatsFullException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.InvalidCourseForCourseTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllCoursesOfTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotEligibleForCourseRegistrationException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRegistrationRepository;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;
import com.wibmo.converter.CourseRegistrationConverter;
import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Payment;
import com.wibmo.entity.Student;
import com.wibmo.entity.Professor;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;

@Service
public class CourseRegistrationServiceImpl implements CourseRegistrationService {
	
	private static final Logger logger = LogManager.getLogger(CourseRegistrationServiceImpl.class);
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	private CourseServiceImpl courseService;
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private CourseRegistrationRepository courseRegistrationRepository;
	
	@Autowired
	private CourseRegistrationConverter courseRegistrationConverter;
	
	@Override
	public void register(CourseRegistrationRequestDTO courseRegistrationRequestDTO)
			throws 
				StudentAlreadyRegisteredForSemesterException, 
				CourseNotExistsInCatalogException, 
				UserNotFoundException, 
				StudentNotEligibleForCourseRegistrationException, 
				InvalidCourseForCourseTypeException, 
				CourseNotAvailableDueToSeatsFullException {
		
		logger.info("courseRegistrationRequestDTO: " + courseRegistrationRequestDTO);
		
		// TODO: Check if Registration is Enabled by Admin

		// TODO: Move to Validator
		
		if(!studentService
				.isStudentExistsById(courseRegistrationRequestDTO.getStudentId())) {
			throw new UserNotFoundException(
					courseRegistrationRequestDTO.getStudentId(), 
					UserType.STUDENT);
		}
		
		Student student = studentService.getStudentById(
				courseRegistrationRequestDTO.getStudentId());
		
		if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(
				userService.getRegistrationStatusById(student.getUserId()))) {
			throw new StudentNotEligibleForCourseRegistrationException(
					courseRegistrationRequestDTO.getStudentId());
		}
		
		if(hasRegistrationByStudentIdAndSemester(
				courseRegistrationRequestDTO.getStudentId(),
				courseRegistrationRequestDTO.getSemester())) {
			throw new StudentAlreadyRegisteredForSemesterException(
					courseRegistrationRequestDTO.getStudentId(), 
					courseRegistrationRequestDTO.getSemester());
		}
		
		for(Integer courseId : courseRegistrationRequestDTO.getPrimaryCourseIds()) {
			if(!courseService.isCourseExistsInCatalog(courseId)) {
				throw new CourseNotExistsInCatalogException(courseId);
			}
			if(!courseService.isCourseHasVacantSeats(courseId)) {
				throw new CourseNotAvailableDueToSeatsFullException(courseId);
			}
			if(!CourseType.PRIMARY.equals(
					courseService.getCourseTypeByCourseId(courseId))) {
				throw new InvalidCourseForCourseTypeException(
						courseId, CourseType.PRIMARY);
			}
		}
		
		for(Integer courseId : courseRegistrationRequestDTO.getAlternativeCourseIds()) {
			if(!courseService.isCourseExistsInCatalog(courseId)) {
				throw new CourseNotExistsInCatalogException(courseId);
			}
			if(!courseService.isCourseHasVacantSeats(courseId)) {
				throw new CourseNotAvailableDueToSeatsFullException(courseId);
			}
			if(!CourseType.ALTERNATIVE.equals(
					courseService.getCourseTypeByCourseId(courseId))) {
				throw new InvalidCourseForCourseTypeException(
						courseId, CourseType.ALTERNATIVE);
			}
		}
			
		courseRegistrationRepository.save(
				courseRegistrationConverter.convert(
						courseRegistrationRequestDTO));
		
		/********************** Save Pending Payment ************************/
		
		CourseRegistration courseRegistration = 
				courseRegistrationRepository.findByStudentIdAndSemester(
						courseRegistrationRequestDTO.getStudentId(),
						courseRegistrationRequestDTO.getSemester()).get();
		
		Payment payment = new Payment();
		payment.setCourseRegistrationId(courseRegistration.getRegistrationId());
		payment.setTotalAmount(4500);
		payment.setPendingAmount(4500);
		payment.setPaymentMode(PaymentMode.CASH);
		
		paymentService.add(payment);
		
		/*********************************************************************/
	}

	@Override
	public List<CourseResponseDTO> getRegisteredCoursesByStudentIdAndSemester(
			Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				UserNotFoundException,
				CourseNotExistsInCatalogException {
		
		Optional<CourseRegistration> courseRegistrationOptional = courseRegistrationRepository
				.findByStudentIdAndSemester(studentId, semester);
		
		if(!courseRegistrationOptional.isPresent()) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		CourseRegistration courseRegistration = courseRegistrationOptional.get();
		Set<Integer> courseIds = new HashSet<>();
		Integer courseId;
		
		// TODO: Move to Join Query to avoid redundant code
		if(null != (courseId = courseRegistration.getPrimaryCourse1Id())) {
			courseIds.add(courseId);
		}
		if(null != (courseId = courseRegistration.getPrimaryCourse2Id())) {
			courseIds.add(courseId);
		}
		if(null != (courseId = courseRegistration.getPrimaryCourse3Id())) {
			courseIds.add(courseId);
		}
		if(null != (courseId = courseRegistration.getPrimaryCourse4Id())) {
			courseIds.add(courseId);
		}
		if(null != (courseId = courseRegistration.getAlternativeCourse1Id())) {
			courseIds.add(courseId);
		}
		if(null != (courseId = courseRegistration.getAlternativeCourse2Id())) {
			courseIds.add(courseId);
		}
		
		return courseService.getCourseDetailsByIds(courseIds);
	}
	
	@Override
	public RegistrationStatus getRegistrationStatusByStudentIdAndSemester(
			Integer studentId, Integer semester) 
			throws StudentNotRegisteredForSemesterException {
		
		Optional<CourseRegistration> courseRegistrationOptional = 
				courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester);
		
		if(!courseRegistrationOptional.isPresent()) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		return courseRegistrationOptional.get().getRegistrationStatus();
	}
	
	@Override
	public void addCourse(Integer courseId, Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentAlreadyRegisteredForCourseInSemesterException, 
				StudentAlreadyRegisteredForAllCoursesOfTypeException,  
				CourseNotExistsInCatalogException, 
				UserNotFoundException {

		logger.info("courseId: " + courseId + ", studentId: " + studentId + ", semester: " + semester);
		
		if(!studentService.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(!courseService.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		Optional<CourseRegistration> courseRegistrationOptional = 
				courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester);
		
		if(!courseRegistrationOptional.isPresent()) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		CourseRegistration courseRegistration = courseRegistrationOptional.get();
		
		if(hasRegistrationForCourse(courseRegistration, courseId)) {
			throw new StudentAlreadyRegisteredForCourseInSemesterException(
					studentId, semester, courseId);
		}
		
		CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
		
		switch(courseType) {
		
		case ALTERNATIVE:
			if(isStudentRegisteredForAllAlternativeCourses(courseRegistration)) {
				throw new StudentAlreadyRegisteredForAllCoursesOfTypeException(
						studentId, courseType);
			}
			if(null == courseRegistration.getAlternativeCourse1Id()) {
				courseRegistration.setAlternativeCourse1Id(courseId);
			} else {
				courseRegistration.setAlternativeCourse2Id(courseId);
			}
			break;
		case PRIMARY:
			if(isStudentRegisteredForAllPrimaryCourses(courseRegistration)) {
				throw new StudentAlreadyRegisteredForAllCoursesOfTypeException(
						studentId, courseType);
			}
			if(null == courseRegistration.getPrimaryCourse1Id()) {
				courseRegistration.setPrimaryCourse1Id(courseId);
			} else if(null == courseRegistration.getPrimaryCourse2Id()) {
				courseRegistration.setPrimaryCourse1Id(courseId);
			} else if(null == courseRegistration.getPrimaryCourse3Id()) {
				courseRegistration.setPrimaryCourse3Id(courseId);
			} else {
				courseRegistration.setPrimaryCourse4Id(courseId);
			}
		}
		
		courseRegistrationRepository.save(courseRegistration);
	}

	@Override
	public void dropCourse(Integer courseId, Integer studentId, Integer semester) 
			throws 
				CourseNotExistsInCatalogException,
				StudentNotRegisteredForSemesterException, 
				StudentNotRegisteredForCourseInSemesterException {
		
		logger.info("courseId: " + courseId + ", studentId: " + studentId + ", semester: " + semester);
		
		Optional<CourseRegistration> courseRegistrationOptional = 
				courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester);
		
		if(!courseRegistrationOptional.isPresent()) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		CourseRegistration courseRegistration = courseRegistrationOptional.get();
		
		if(!hasRegistrationForCourse(courseRegistration, courseId)) {
			throw new StudentNotRegisteredForCourseInSemesterException(studentId, semester, courseId);
		}
		
		CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
		
		switch(courseType) {
		
		case ALTERNATIVE:
			if(courseRegistration.getAlternativeCourse1Id().equals(courseId)) {
				courseRegistration.setAlternativeCourse1Id(null);
			} else {
				courseRegistration.setAlternativeCourse2Id(null);
			}
			break;
		
		case PRIMARY:
			if(courseRegistration.getPrimaryCourse1Id().equals(courseId)) {
				courseRegistration.setPrimaryCourse1Id(null);
			} else if(courseRegistration.getPrimaryCourse2Id().equals(courseId)) {
				courseRegistration.setPrimaryCourse2Id(null);
			} else if(courseRegistration.getPrimaryCourse3Id().equals(courseId)) {
				courseRegistration.setPrimaryCourse3Id(null);
			} else {
				courseRegistration.setPrimaryCourse4Id(null);
			}
		}
		
		courseRegistrationRepository.save(courseRegistration);
	}

	@Override
	public List<Student> getRegisteredStudentsByCourseId(Integer courseId) throws CourseNotExistsInCatalogException {
		
		if(!courseService.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
		
		List<CourseRegistration> courseRegistrations = new ArrayList<>();
		
		switch(courseType) {
		case ALTERNATIVE:
			courseRegistrations.addAll(
					courseRegistrationRepository
						.findAllByAlternativeCourse1IdIn(
								Collections.singletonList(courseId)));
			courseRegistrations.addAll(
					courseRegistrationRepository
						.findAllByAlternativeCourse2IdIn(
								Collections.singletonList(courseId)));
			break;
		case PRIMARY:
			courseRegistrations.addAll(
					courseRegistrationRepository
						.findAllByPrimaryCourse1IdIn(
								Collections.singletonList(courseId)));
			courseRegistrations.addAll(
					courseRegistrationRepository
						.findAllByPrimaryCourse2IdIn(
								Collections.singletonList(courseId)));
			courseRegistrations.addAll(
					courseRegistrationRepository
						.findAllByPrimaryCourse3IdIn(
								Collections.singletonList(courseId)));
			courseRegistrations.addAll(
					courseRegistrationRepository
						.findAllByPrimaryCourse4IdIn(
								Collections.singletonList(courseId)));
		}
		
		return studentService
				.getAllStudentsByIds(
						courseRegistrations
							.stream()
							.map(CourseRegistration::getStudentId)
							.collect(Collectors.toSet()));
	}

	// TODO: This implementation should be improved using Join query
	// TODO: This method is NOT tested yet.
	@Override
	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId)
			throws UserNotFoundException {
		
		
		if(null==professorId||!professorService.isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		
		Map<Integer, List<Student>> courseIdToRegisteredStudentsMap = new TreeMap<>();
		
		
		for(Tuple t:courseRegistrationRepository.getCourseIdToRegisteredStudentsMappingByProfessorId(professorId)) {
			
			Student student = new Student((int)t.get(0),(int)t.get(1),(String)t.get(2),(String)t.get(3),(int)t.get(4));
			if(courseIdToRegisteredStudentsMap.containsKey((int)t.get(5))) {
				courseIdToRegisteredStudentsMap.get((int)t.get(5)).add(student);
			}
			else {
				List<Student> tempStudent= new ArrayList<Student>();
				tempStudent.add(student);
				courseIdToRegisteredStudentsMap.put((int)t.get(5), tempStudent);
			}

		}

		return courseIdToRegisteredStudentsMap;
		
	}
	
	@Override
	public List<CourseRegistrationResponseDTO> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus){
		List<CourseRegistration> courseRegistrations = courseRegistrationRepository
				.findAllByRegistrationStatus(registrationStatus);
		List<Integer> courseIds = getCourseIds(courseRegistrations);
		Map<Integer, Course> courseIdToCourseMap = courseService.getCourseIdToCourseMap(courseIds);
		Map<Integer, Professor> professorIdToProfessorMap = professorService.getProfessorIdToProfessorMap(
				courseIdToCourseMap
					.entrySet()
					.stream()
					.map(entry -> entry.getValue().getProfessorId())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		
		return courseRegistrationConverter.convertAll(
				courseRegistrations,
				courseIdToCourseMap,
				professorIdToProfessorMap);
	}
	
	@Override
	public Boolean updateCourseRegistrationStatusToByRegistrationIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> courseRegistrationIds) 
					throws CannotApproveCourseRegistrationPaymentPendingException {
		
		logger.info("registrationStatus: " + registrationStatus.toString());
		logger.info("courseRegistrationIds: " + courseRegistrationIds);
		
		List<CourseRegistration> courseRegistrations = 
				courseRegistrationRepository.findAllByRegistrationIdIn(courseRegistrationIds);
		
		/*
		 * We cannot Approve Registration until the Student's pending dues are clear.
		 */
		if(RegistrationStatus.APPROVED.equals(registrationStatus)) {
			for(CourseRegistration courseRegistration : courseRegistrations) {
				if(PaymentStatus.UNPAID.equals(
						paymentService.getPaymentStatusByCourseRegistrationId(
						courseRegistration.getRegistrationId()))) {
					throw new CannotApproveCourseRegistrationPaymentPendingException(
							courseRegistration.getRegistrationId());
				}
			}
		}
		
		courseRegistrations.forEach(courseRegistration -> {
			courseRegistration.setRegistrationStatus(registrationStatus);
			/*
			 * Also, decrement available seat count
			 */
			courseService.decrementNumOfSeatsByCourseIds(
						getRegisteredCourseIdsByRegistrationId(
								courseRegistration.getRegistrationId()));
		});
		
		courseRegistrationRepository.saveAll(courseRegistrations);
		
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean updateAllPendingCourseRegistrationsTo(
			RegistrationStatus registrationStatus) 
					throws CannotApproveCourseRegistrationPaymentPendingException {
		
		List<CourseRegistration> courseRegistrations = 
				courseRegistrationRepository.findAllByRegistrationStatus(
						RegistrationStatus.PENDING);
		
		/*
		 * We cannot Approve Registration until the Student's pending dues are clear.
		 */
		if(RegistrationStatus.APPROVED.equals(registrationStatus)) {
			for(CourseRegistration courseRegistration : courseRegistrations) {
				if(PaymentStatus.UNPAID.equals(
						paymentService.getPaymentStatusByCourseRegistrationId(
						courseRegistration.getRegistrationId()))) {
					throw new CannotApproveCourseRegistrationPaymentPendingException(
							courseRegistration.getRegistrationId());
				}
			}
		}
		
		courseRegistrations.forEach(courseRegistration -> {
				courseRegistration.setRegistrationStatus(registrationStatus);
				/*
				 * Also, decrement available seat count
				 */
				courseService.decrementNumOfSeatsByCourseIds(
							getRegisteredCourseIdsByRegistrationId(
									courseRegistration.getRegistrationId()));
		});
		
		courseRegistrationRepository.saveAll(courseRegistrations);
		
		return Boolean.TRUE;
	}

	@Override
	public List<Student> getRegisteredStudentsByProfessorIdAndCourseId(
		Integer professorId, Integer courseId)
			throws 
				CourseNotExistsInCatalogException,
				UserNotFoundException, 
				ProfessorNotAssignedForCourseException {
		
		if(null == professorId || null == courseId) {
			return Collections.emptyList();
		}
		
		if(!professorService.isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		if(!courseService.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!courseService.isProfessorAssignedForCourse(professorId, courseId)) {
			throw new ProfessorNotAssignedForCourseException(professorId, courseId);
		}
		
		return getRegisteredStudentsByCourseId(courseId);
	}
	
	@Override
	public Boolean hasRegistrationByStudentIdAndCourseId(
			Integer studentId, Integer courseId) 
				throws 
					UserNotFoundException, 
					CourseNotExistsInCatalogException {
		
		if(!studentService.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(!courseService.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
		
		if(CourseType.ALTERNATIVE.equals(courseType)) {
			return courseRegistrationRepository.existsByStudentIdAndAlternativeCourse1Id(studentId, courseId)
				|| courseRegistrationRepository.existsByStudentIdAndAlternativeCourse2Id(studentId, courseId);
		}
		
		return courseRegistrationRepository.existsByStudentIdAndPrimaryCourse1Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse2Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse3Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse4Id(studentId, courseId);
	}
	
	@Override
	public Boolean hasRegistrationByStudentIdAndSemester(
			Integer studentId, Integer semester) throws UserNotFoundException {
		
		if(!studentService.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		return courseRegistrationRepository
				.existsByStudentIdAndSemester(studentId, semester);
	}
	
	@Override
	public CourseRegistration getCourseRegistrationByStudentIdAndSemester(Integer studentId, Integer semester) {
		return courseRegistrationRepository
				.findByStudentIdAndSemester(studentId, semester)
				.map(courseRegistration -> courseRegistration)
				.orElse(null);
		
	}
	
	@Override
	public List<Integer> getRegisteredCourseIdsByRegistrationId(Integer registrationId) {
		return courseRegistrationRepository
				.findByRegistrationId(registrationId)
				.map(courseRegistration -> getCourseIds(Collections.singletonList(courseRegistration)))
				.orElse(Collections.emptyList());
	}
	
	/*************************** Utility Methods ********************************/
	
	private Boolean isStudentRegisteredForAllAlternativeCourses(CourseRegistration courseRegistration) {
		return null != courseRegistration.getAlternativeCourse1Id()
			&& null != courseRegistration.getAlternativeCourse2Id();
	}
	
	private Boolean isStudentRegisteredForAllPrimaryCourses(CourseRegistration courseRegistration) {
		return null != courseRegistration.getPrimaryCourse1Id()
			&& null != courseRegistration.getPrimaryCourse2Id()
			&& null != courseRegistration.getPrimaryCourse3Id()
			&& null != courseRegistration.getPrimaryCourse4Id();
	}
	
	private Boolean hasRegistrationForCourse(
			CourseRegistration courseRegistration,
			Integer courseId) {
		return (null != courseRegistration.getPrimaryCourse1Id()
				&& courseRegistration.getPrimaryCourse1Id().equals(courseId))
			|| (null != courseRegistration.getPrimaryCourse2Id()
				&& courseRegistration.getPrimaryCourse2Id().equals(courseId))
			|| (null != courseRegistration.getPrimaryCourse3Id()
				&& courseRegistration.getPrimaryCourse3Id().equals(courseId))
			|| (null != courseRegistration.getPrimaryCourse4Id()
				&& courseRegistration.getPrimaryCourse4Id().equals(courseId))
			|| (null != courseRegistration.getAlternativeCourse1Id()
				&& courseRegistration.getAlternativeCourse1Id().equals(courseId))
			|| (null != courseRegistration.getAlternativeCourse2Id()
				&& courseRegistration.getAlternativeCourse2Id().equals(courseId));
	}
	
	private List<Integer> getCourseIds(List<CourseRegistration> courseRegistrations) {
		List<Integer> courseIds = new ArrayList<>();
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse1Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse2Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse3Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse4Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getAlternativeCourse1Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getAlternativeCourse2Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		return courseIds;
	}

}
