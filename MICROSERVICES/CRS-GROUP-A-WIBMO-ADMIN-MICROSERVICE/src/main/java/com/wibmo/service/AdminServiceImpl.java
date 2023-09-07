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
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.converter.CourseConverter;
import com.wibmo.converter.CourseRegistrationConverter;
import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Professor;
import com.wibmo.entity.User;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CannotApproveCourseRegistrationPaymentPendingException;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.AdminRepository;
import com.wibmo.repository.CourseRegistrationRepository;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.UserRepository;

/**
 * 
 */
@Service
public class AdminServiceImpl implements AdminService {
	
	private static Logger logger =LogManager.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private CourseRegistrationRepository courseRegistrationRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseConverter courseConverter;
	
	@Autowired
	private CourseRegistrationConverter courseRegistrationConverter;
	
	@Override
	public Admin getAdminById(Integer adminId) {
		Optional<Admin> adminOptional = adminRepository.findByAdminId(adminId);
		return adminOptional.isPresent()
				? adminOptional.get()
				: null;
	}
	
	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}
	
	@Override
	public void add(Admin admin) {
		
		if(null == admin) {
			return;
		}
		
		adminRepository.save(admin);
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
		
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		Collection<Integer> professorIds = 
							courses
							.stream()
							.map(Course::getProfessorId)
							.filter(Objects::nonNull)
							.collect(Collectors.toSet());
		Map<Integer, Professor> professorIdToProfessorMap=professorRepository
		.findAllByProfessorIdIn(professorIds)
		.stream()
		.collect(Collectors.toMap(
				Professor::getProfessorId, 
				Function.identity()));
		return courseConverter.convertAll(courseRepository.findAllByCourseIdIn(courseIds),professorIdToProfessorMap);
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
	public List<CourseRegistrationResponseDTO> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus){
		List<CourseRegistration> courseRegistrations = courseRegistrationRepository
				.findAllByRegistrationStatus(registrationStatus);
		List<Integer> courseIds = getCourseIds(courseRegistrations);
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		Collection<Integer> professorIds = 
							courses
							.stream()
							.map(Course::getProfessorId)
							.filter(Objects::nonNull)
							.collect(Collectors.toSet());
		Map<Integer, Course> courseIdToCourseMap = courseRepository
				.findAllByCourseIdIn(courseIds)
				.stream()
				.collect(Collectors.toMap(
						Course::getCourseId,
						Function.identity()));
		Map<Integer, Professor> professorIdToProfessorMap = professorRepository
				.findAllByProfessorIdIn(professorIds)
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId, 
						Function.identity()));
		
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
						paymentRepository
						.findByCourseRegistrationId(courseRegistration.getRegistrationId())
						.map(payment -> payment.getPendingAmount() == 0 
							? PaymentStatus.PAID : PaymentStatus.UNPAID)
						.orElse(null))) {
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
			decrementNumOfSeatsByCourseIds(
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
						paymentRepository.findByCourseRegistrationId(
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
				decrementNumOfSeatsByCourseIds(
							getRegisteredCourseIdsByRegistrationId(
									courseRegistration.getRegistrationId()));
		});
		
		courseRegistrationRepository.saveAll(courseRegistrations);
		
		return Boolean.TRUE;
	}
	@Override
	public Boolean add(CourseRequestDTO courseRequestDTO) {
		
		// TODO: Move to Validator
		if(null == courseRequestDTO) {
			return Boolean.FALSE;
		}
		
		// TODO: Add more input validations
		
		courseRepository.save(courseConverter.convert(courseRequestDTO)); 
		
		return Boolean.TRUE;
	}

	@Override
	public Boolean addAll(Collection<CourseRequestDTO> courseRequestDTOs) {
		
		if(null == courseRequestDTOs) {
			return Boolean.FALSE;
		}
		
		courseRepository.saveAll(courseConverter.convertAll(courseRequestDTOs));
		
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean removeCourseById(Integer courseId) 
			throws 
				CourseNotExistsInCatalogException, 
				CannotDropCourseAssignedToProfessorException {
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		Integer professorId = courseRepository
								.findByCourseId(courseId)
								.get()
								.getProfessorId();
		
		if(null != professorId) {
			throw new CannotDropCourseAssignedToProfessorException(courseId, professorId);
		}
		
		courseRepository.delete(courseOptional.get());
		
		return Boolean.TRUE;
	}

	@Override
	public void assignCourseToProfessor(Integer courseId, Integer professorId) 
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException {
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!professorRepository.existsByProfessorId(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		// TODO: If professor registration is not approved,
		// we cannot assign course to him/her.
		
		Course course = courseOptional.get();
		
		course.setProfessorId(professorId);
		
		courseRepository.save(course);
	}
	@Override
	public List<Integer> getRegisteredCourseIdsByRegistrationId(Integer registrationId) {
		return courseRegistrationRepository
				.findByRegistrationId(registrationId)
				.map(courseRegistration -> getCourseIds(Collections.singletonList(courseRegistration)))
				.orElse(Collections.emptyList());
	}
	@Override
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> userIds) 
					throws UserNotFoundException {
		
		if(null == userIds || userIds.isEmpty()) {
			return Boolean.FALSE;
		}
		
		
		List<User> users = userRepository.findAllByUserIdIn(userIds);
		
		if(users.size()!=userIds.size()) {
			return Boolean.FALSE;
		}
		users.forEach(user -> user.setRegistrationStatus(registrationStatus));
		
		userRepository.saveAll(users);
		
		return Boolean.TRUE;
		
	}
	
	@Override
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus) {
		
		List<User> pendingAccounts = userRepository
					.findAllByRegistrationStatus(
						RegistrationStatus.PENDING);
		
		pendingAccounts.forEach(
				account -> account
					.setRegistrationStatus(
						registrationStatus));
		
		userRepository.saveAll(pendingAccounts);
		
		return Boolean.TRUE;
	}
	
	// --------------------------------UTILITY METHODS---------------------------
	
	/**
	 * Fetches the Course Ids based on given List of courseRegistration
	 * @param courseRegistrations
	 * @return
	 */
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
	
	/**
	 * Decreases the number of Seats if the Seat is Filled.
	 * @param courseIds
	 */
	public void decrementNumOfSeatsByCourseIds(List<Integer> courseIds) {
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		courses.forEach(course -> course.setNoOfSeats(course.getNoOfSeats() - 1));
		courseRepository.saveAll(courses);
	}
	public Map<Integer, Course> getcourseIdToCourseMap(List<Integer> courseIds) {
		return courseRepository
				.findAllByCourseIdIn(courseIds)
				.stream()
				.collect(Collectors.toMap(
						Course::getCourseId,
						Function.identity()));
	}
	public Map<Integer, Professor> getprofessorIdToProfessorMap(List<Integer> professorIds){
		return professorRepository
		.findAllByProfessorIdIn(professorIds)
		.stream()
		.collect(Collectors.toMap(
				Professor::getProfessorId, 
				Function.identity()));
	}
}