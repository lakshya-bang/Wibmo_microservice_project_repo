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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.converter.CourseConverter;
import com.wibmo.converter.CourseRegistrationConverter;
import com.wibmo.converter.ReportCardConverter;
import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.dto.ReportCardResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Payment;
import com.wibmo.entity.Professor;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CourseNotAvailableDueToSeatsFullException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.InvalidCourseForCourseTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllCoursesOfTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotEligibleForCourseRegistrationException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRegistrationRepository;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.ReportCardRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;
import com.wibmo.utils.CourseRegistrationUtils;
import com.wibmo.utils.CourseUtils;
import com.wibmo.utils.ProfessorUtils;
import com.wibmo.utils.StudentUtils;

/**
 * 
 */
@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseRegistrationRepository courseRegistrationRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ReportCardRepository reportCardRepository;
	
	@Autowired
	private CourseConverter courseConverter;
	
	@Autowired
	private CourseRegistrationConverter courseRegistrationConverter;
	
	@Autowired
	private ReportCardConverter reportCardConverter;
	
	@Override
	public Student getStudentById(Integer studentId) {
		Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
		return studentOptional.isPresent()
				? studentOptional.get()
				: null;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds) {
		logger.info("student ids: " + studentIds);
		//TODO: check if all studentIds Exists
		if(null == studentIds) {
			return new ArrayList<Student>();
		}
		List<Integer> nonExistingIds = 
			studentIds
				.stream()
				.filter(studentId -> !studentRepository.existsByStudentId(studentId))
				.collect(Collectors.toList());
		logger.info("User with id/ids: " + nonExistingIds + " does not exist/exists.");
		
		return studentRepository.findAllByStudentIdIn(studentIds);
	}

	@Override
	public void add(Student student) {
		if(null == student) {
			return;
		}
		studentRepository.save(student);
	}
	
	@Override
	public Boolean isStudentExistsById(Integer studentId){
		return studentRepository.existsByStudentId(studentId);
	}
	
	@Override
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> studentIds) {
		return StudentUtils.getStudentIdToStudentMap(
				studentRepository.findAllByStudentIdIn(studentIds));
	}
	
	@Override
	public Integer getCurrentSemesterByStudentId(Integer studentId) throws UserNotFoundException {
		if(!isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		return studentRepository
				.findByStudentId(studentId)
				.get()
				.getCurrentSemester();
	}
	
	/**************************** Course Methods *****************************/
	
	@Override
	public CourseResponseDTO getCourseDetailsById(Integer courseId) {
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		if(courseOptional.isEmpty()) {
			return null;
		}
		Course course = courseOptional.get();
		Professor professor = null;
		if(null != course.getProfessorId()) {
			Optional<Professor> professorOptional = professorRepository.findByProfessorId(course.getProfessorId());
			if(professorOptional.isPresent()) {
				professor = professorOptional.get();
			}
		}
		return courseConverter.convert(course, professor);
	}
	
	@Override
	public List<CourseResponseDTO> getCourseDetailsByIds(Collection<Integer> courseIds) {
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		return courseConverter.convertAll(
				courses, 
				ProfessorUtils.getProfessorIdToProfessorMap(
						professorRepository.findAllByProfessorIdIn(
								CourseUtils.getProfessorIds(courses))));
	}
	
	@Override
	public List<CourseResponseDTO> getAllCourses() {
		List<Course> courses = courseRepository.findAll();
		return courseConverter.convertAll(
				courses, 
				ProfessorUtils.getProfessorIdToProfessorMap(
						professorRepository.findAllByProfessorIdIn(
								CourseUtils.getProfessorIds(courses))));
	}
	
	// TODO: This implementation can be moved to Join query in Database
	@Override
	public List<CourseResponseDTO> getCourseDetailsBySemester(Integer semester) {
		List<Course> courses = courseRepository.findAllBySemester(semester);
		return courseConverter.convertAll(
				courses, 
				ProfessorUtils.getProfessorIdToProfessorMap(
						professorRepository.findAllByProfessorIdIn(
								CourseUtils.getProfessorIds(courses))));
	}

	@Override
	public Map<Integer, Course> getCourseIdToCourseMap(Collection<Integer> courseIds) {
		return CourseUtils.getCourseIdToCourseMap(
				courseRepository.findAllByCourseIdIn(courseIds));
	}
	
	@Override
	public CourseType getCourseTypeByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException {
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		if(courseOptional.isEmpty()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		return courseOptional.get().getCourseType();
	}

	@Override
	public Boolean isCourseExistsInCatalog(Integer courseId) {
		return courseRepository
				.existsByCourseId(courseId);
	}

	@Override
	public void decrementNumOfSeatsByCourseIds(List<Integer> courseIds) {
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		courses.forEach(course -> course.setNoOfSeats(course.getNoOfSeats() - 1));
		courseRepository.saveAll(courses);
	}

	@Override
	public Boolean isCourseHasVacantSeats(Integer courseId) {
		return courseRepository
				.findByCourseId(courseId)
				.get()
				.getNoOfSeats() > 0;
	}
	
	/************************* Course-Registration Methods ****************************/
	
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
		
		Optional<Student> studentOptional = studentRepository.findByStudentId(
				courseRegistrationRequestDTO.getStudentId());
		
		if(!studentOptional.isPresent()) {
			throw new UserNotFoundException(
					courseRegistrationRequestDTO.getStudentId(), 
					UserType.STUDENT);
		}
		
		RegistrationStatus registrationStatus = userRepository
					.findByUserId(
						studentOptional.get().getUserId())
					.get()
					.getRegistrationStatus();
		
		if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(registrationStatus)) {
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
		
		// TODO: Move to an @Query method that should return the list of invalid courseIds 
		for(Integer courseId : courseRegistrationRequestDTO.getPrimaryCourseIds()) {
			
			Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
			
			if(!courseOptional.isPresent()) {
				throw new CourseNotExistsInCatalogException(courseId);
			}
			if(courseOptional.get().getNoOfSeats() == 0) {
				throw new CourseNotAvailableDueToSeatsFullException(courseId);
			}
			if(!CourseType.PRIMARY.equals(courseOptional.get().getCourseType())) {
				throw new InvalidCourseForCourseTypeException(
						courseId, CourseType.PRIMARY);
			}
		}
		
		// TODO: Move to an @Query method that should return the list of invalid courseIds
		for(Integer courseId : courseRegistrationRequestDTO.getAlternativeCourseIds()) {
			
			Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
			
			if(!courseOptional.isPresent()) {
				throw new CourseNotExistsInCatalogException(courseId);
			}
			if(courseOptional.get().getNoOfSeats() == 0) {
				throw new CourseNotAvailableDueToSeatsFullException(courseId);
			}
			if(!CourseType.ALTERNATIVE.equals(courseOptional.get().getCourseType())) {
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
		
		paymentRepository.save(payment);
		
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
		
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		
		return courseConverter.convertAll(
				courses,
				ProfessorUtils.getProfessorIdToProfessorMap(
						professorRepository.findAllByProfessorIdIn(
								courses
									.stream()
									.map(Course::getProfessorId)
									.filter(Objects::nonNull)
									.collect(Collectors.toSet()))));
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
		
		if(!isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
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
		
		CourseType courseType = courseOptional.get().getCourseType();
		
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
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		Optional<CourseRegistration> courseRegistrationOptional = 
				courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester);
		
		if(!courseRegistrationOptional.isPresent()) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		CourseRegistration courseRegistration = courseRegistrationOptional.get();
		
		if(!hasRegistrationForCourse(courseRegistration, courseId)) {
			throw new StudentNotRegisteredForCourseInSemesterException(studentId, semester, courseId);
		}
		
		CourseType courseType = courseOptional.get().getCourseType();
		
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
	public List<Student> getRegisteredStudentsByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException {
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		CourseType courseType = courseOptional.get().getCourseType();
		
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
		
		return studentRepository
					.findAllByStudentIdIn(
						courseRegistrations
							.stream()
							.map(CourseRegistration::getStudentId)
							.collect(Collectors.toSet()));
	}
	
	@Override
	public List<CourseRegistrationResponseDTO> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus){
		List<CourseRegistration> courseRegistrations = courseRegistrationRepository
				.findAllByRegistrationStatus(registrationStatus);
		Collection<Course> courses = courseRepository.findAllByCourseIdIn(
				CourseRegistrationUtils.getCourseIds(courseRegistrations));
		Collection<Professor> professors = professorRepository.findAllByProfessorIdIn(
				CourseUtils.getProfessorIds(courses));
		
		return courseRegistrationConverter.convertAll(
				courseRegistrations,
				CourseUtils.getCourseIdToCourseMap(courses),
				ProfessorUtils.getProfessorIdToProfessorMap(professors));
	}
	
	@Override
	public Boolean hasRegistrationByStudentIdAndCourseId(
			Integer studentId, Integer courseId) 
				throws 
					UserNotFoundException, 
					CourseNotExistsInCatalogException {
		
		if(!isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		CourseType courseType = courseOptional.get().getCourseType();
		
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
		
		if(!isStudentExistsById(studentId)) {
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
				.map(courseRegistration -> {
					List<Integer> courseIds = new ArrayList<>();
					if(null != courseRegistration.getPrimaryCourse1Id()) {
						courseIds.add(courseRegistration.getPrimaryCourse1Id());
					}
					if(null != courseRegistration.getPrimaryCourse2Id()) {
						courseIds.add(courseRegistration.getPrimaryCourse2Id());
					}
					if(null != courseRegistration.getPrimaryCourse3Id()) {
						courseIds.add(courseRegistration.getPrimaryCourse3Id());
					}
					if(null != courseRegistration.getPrimaryCourse4Id()) {
						courseIds.add(courseRegistration.getAlternativeCourse1Id());
					}
					if(null != courseRegistration.getAlternativeCourse1Id()) {
						courseIds.add(courseRegistration.getAlternativeCourse1Id());
					}
					if(null != courseRegistration.getAlternativeCourse2Id()) {
						courseIds.add(courseRegistration.getAlternativeCourse2Id());
					}
					return courseIds;
				}).orElse(Collections.emptyList());
	}
	
	/***************************** Report Card Methods ********************************/
	
	@Override
	public Map<Integer, List<ReportCardResponseDTO>> getSemesterToReportCardMapByStudentId(
			Integer studentId) throws UserNotFoundException {
		
		if(!studentRepository.existsByStudentId(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		// We will sort the Map by semester
		Map<Integer, List<ReportCardResponseDTO>> semesterToReportCardDTOMap = new TreeMap<>();
				
		List<ReportCard> reportCards = reportCardRepository.findAllByStudentId(studentId);
		
		Map<Integer, Course> courseIdToCourseMap = CourseUtils.getCourseIdToCourseMap(
				courseRepository.findAllByCourseIdIn(
						reportCards
							.stream()
							.map(ReportCard::getCourseId)
							.collect(Collectors.toSet())));
							
		reportCards
			.forEach(reportCard -> {
				int semester = reportCard.getSemester();
				if(!semesterToReportCardDTOMap.containsKey(semester)) {
					semesterToReportCardDTOMap.put(semester, new ArrayList<>());
				}
				semesterToReportCardDTOMap
					.get(semester)
					.add(reportCardConverter.convert(
							reportCard,
							courseIdToCourseMap));
			});
		
		return semesterToReportCardDTOMap;
	}
	
	@Override
	public ReportCardResponseDTO getReportCardByStudentIdAndCourseId(Integer studentId, Integer courseId) 
			throws 
				UserNotFoundException, 
				CourseNotExistsInCatalogException, 
				StudentNotRegisteredForCourseException {
		
		if(!isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(!courseRepository.existsByCourseId(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!(courseRegistrationRepository.existsByStudentIdAndPrimaryCourse1Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse2Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse3Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse4Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndAlternativeCourse1Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndAlternativeCourse2Id(studentId, courseId))) {
			throw new StudentNotRegisteredForCourseException(studentId, courseId);
		}
		
		Optional<ReportCard> reportCardOptional = reportCardRepository
				.findByStudentIdAndCourseId(studentId, courseId);
				
		return reportCardConverter.convert(
					reportCardOptional.get(), 
					CourseUtils.getCourseIdToCourseMap(
							Collections.singletonList(
									courseRepository.findByCourseId(courseId).get())));
								
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
		
		if(!isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(!courseRegistrationRepository.existsByStudentIdAndSemester(studentId, semester)) {
			throw new StudentNotRegisteredForSemesterException(studentId, semester);
		}
		
		List<ReportCard> reportCards = reportCardRepository.findAllByStudentIdAndSemester(studentId, semester);
		
		return reportCardConverter.convertAll(
				reportCards, 
				CourseUtils.getCourseIdToCourseMap(
						courseRepository.findAllByCourseIdIn(
								reportCards
									.stream()
									.map(ReportCard::getCourseId)
									.collect(Collectors.toSet()))));
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
}
