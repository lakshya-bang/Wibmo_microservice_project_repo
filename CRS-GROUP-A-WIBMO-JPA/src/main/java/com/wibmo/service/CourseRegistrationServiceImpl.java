package com.wibmo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRegistrationRepository;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;

//@Service
//public class CourseRegistrationServiceImpl implements CourseRegistrationService {
//	
//	private static final Logger logger = LogManager.getLogger(CourseRegistrationServiceImpl.class);
//	
//	@Autowired
//	private UserServiceImpl userOperation;
//	
//	@Autowired
//	private StudentServiceImpl studentService;
//	
//	@Autowired
//	private ProfessorServiceImpl professorService;
//	
//	@Autowired
//	private CourseServiceImpl courseService;
//	
//	@Autowired
//	private CourseRegistrationRepository courseRegistrationRepository;
//	
//	@Override
//	public void register(
//			List<Integer> primaryCourseIds, 
//			List<Integer> alternativeCourseIds, 
//			Student student)
//			throws 
//				StudentAlreadyRegisteredForSemesterException, 
//				CourseNotExistsInCatalogException {
//		
//		logger.info("primaryCourseIds: " + primaryCourseIds);
//		logger.info("alternativeCourseIds: " + alternativeCourseIds);
//		logger.info("student: " + student);
//		
//		// TODO: Check if Registration is Enabled by Admin
//
//		if(hasRegistrationByStudentIdAndSemester(
//				student.getStudentId(), student.getCurrentSemester())) {
//			throw new StudentAlreadyRegisteredForSemesterException(student);
//		}
//		
//		for(Integer courseId : primaryCourseIds) {
//			if(!courseService.isCourseExistsInCatalog(courseId)) {
//				throw new CourseNotExistsInCatalogException(courseId);
//			}
//		}
//		for(Integer courseId : alternativeCourseIds) {
//			if(!courseService.isCourseExistsInCatalog(courseId)) {
//				throw new CourseNotExistsInCatalogException(courseId);
//			}
//		}
//		
//		CourseRegistration courseRegistration = new CourseRegistration();
//		courseRegistration.setStudentId(student.getStudentId());
//		courseRegistration.setSemester(student.getCurrentSemester());
//		courseRegistration.setYear(LocalDate.now().getYear());
//		courseRegistration.setPrimaryCourse1Id(primaryCourseIds.get(0));
//		courseRegistration.setPrimaryCourse2Id(primaryCourseIds.get(1));
//		courseRegistration.setPrimaryCourse3Id(primaryCourseIds.get(2));
//		courseRegistration.setPrimaryCourse4Id(primaryCourseIds.get(3));
//		courseRegistration.setAlternativeCourse1Id(alternativeCourseIds.get(0));
//		courseRegistration.setAlternativeCourse2Id(alternativeCourseIds.get(1));
//		courseRegistration.setRegistrationStatus(RegistrationStatus.PENDING);
//		
//		courseRegistrationRepository.save(courseRegistration);
//		
//	}
//
//	@Override
//	public List<CourseProfessorDTO> getRegisteredCoursesByStudent(Student student) 
//			throws StudentNotRegisteredForSemesterException, UserNotFoundException, CourseNotExistsInCatalogException {
//		
//		if(!hasRegistrationByStudentIdAndCourseId(
//				student.getStudentId(), 
//				student.getCurrentSemester())) {
//			throw new StudentNotRegisteredForSemesterException(
//					student.getStudentId(),
//					student.getCurrentSemester());
//		}
//		
//		CourseRegistration courseRegistration = courseRegistrationRepository
//				.findByStudentIdAndSemester(
//						student.getStudentId(),
//						student.getCurrentSemester());
//		Set<Integer> courseIds = new HashSet<>();
//		Integer courseId;
//		
//		// TODO: Move to Join Query to avoid redundant code
//		if(null != (courseId = courseRegistration.getPrimaryCourse1Id())) {
//			courseIds.add(courseId);
//		}
//		if(null != (courseId = courseRegistration.getPrimaryCourse2Id())) {
//			courseIds.add(courseId);
//		}
//		if(null != (courseId = courseRegistration.getPrimaryCourse3Id())) {
//			courseIds.add(courseId);
//		}
//		if(null != (courseId = courseRegistration.getPrimaryCourse4Id())) {
//			courseIds.add(courseId);
//		}
//		if(null != (courseId = courseRegistration.getAlternativeCourse1Id())) {
//			courseIds.add(courseId);
//		}
//		if(null != (courseId = courseRegistration.getAlternativeCourse2Id())) {
//			courseIds.add(courseId);
//		}
//		Map<Integer, Course> courseIdToCourseMap = courseService
//				.getCourseIdToCourseMap(courseIds);
//		Map<Integer, Professor> professorIdToProfessorMap = professorService.getProfessorIdToProfessorMap(
//				courseIdToCourseMap
//					.entrySet()
//					.stream()
//					.map(entry -> entry.getValue().getProfessorId())
//					.collect(Collectors.toSet()));
//		
//		return courseIdToCourseMap
//			.entrySet()
//			.stream()
//			.map(entry -> entry.getValue())
//			.map(course -> new CourseProfessorDTO(
//								course,
//								professorIdToProfessorMap.get(
//									course.getProfessorId())))
//			.collect(Collectors.toList());
//	}
//	
//	@Override
//	public RegistrationStatus getRegistrationStatusByStudent(Student student) 
//			throws StudentNotRegisteredForSemesterException {
//		
//		if(!hasRegistrationByStudentIdAndSemester(
//				student.getStudentId(), student.getCurrentSemester())) {
//			throw new StudentNotRegisteredForSemesterException(
//					student.getStudentId(),
//					student.getCurrentSemester());
//		}
//		
//		return courseRegistrationRepository.findRegistrationStatusByStudent(student);
//	}
//	
//	@Override
//	public void addCourse(Integer courseId, Student student) 
//			throws 
//				StudentNotRegisteredForSemesterException, 
//				StudentAlreadyRegisteredForCourseInSemesterException, 
//				StudentAlreadyRegisteredForAllAlternativeCoursesException, 
//				StudentAlreadyRegisteredForAllPrimaryCoursesException, 
//				CourseNotExistsInCatalogException {
//
//		logger.info("courseId: " + courseId + ", student: " + student);
//		
//		if(!hasRegistrationByStudentIdAndSemester(
//				student.getStudentId(),
//				student.getCurrentSemester())) {
//			throw new StudentNotRegisteredForSemesterException(
//					student.getStudentId(),
//					student.getCurrentSemester());
//		}
//		
//		if(!courseService.isCourseExistsInCatalog(courseId)) {
//			throw new CourseNotExistsInCatalogException(courseId);
//		}
//		
//		// TODO: Improve dual DB call for same thing. Handle with index. 
//		if(isStudentRegisteredForCourse(student, courseId)) {
//			throw new StudentAlreadyRegisteredForCourseInSemesterException(student, courseId);
//		}
//		
//		// TODO: CourseLite object can help avoid redundant DB access
//		CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
//				
//		switch(courseType) {
//		
//		case ALTERNATIVE:
//			if(isStudentRegisteredForAllAlternativeCourses(student)) {
//				throw new StudentAlreadyRegisteredForAllAlternativeCoursesException(student);
//			}
//			break;
//		case PRIMARY:
//			if(isStudentRegisteredForAllPrimaryCourses(student)) {
//				throw new StudentAlreadyRegisteredForAllPrimaryCoursesException(student);
//			}
//		}
//		
//		Integer courseRegistrationId = courseRegistrationRepository
//				.findCourseRegistrationIdByStudent(student);
//		
//		
//		switch(courseType) {
//			
//		case ALTERNATIVE:
//			courseRegistrationRepository
//				.setAlternativeCourseIdAsAtIndexByCourseRegistrationId(
//					courseId,
//					courseRegistrationRepository
//						.findFirstVacantAlternativeCourseIdIndexByCourseRegistrationId(
//							courseRegistrationId),
//					courseRegistrationId);
//			break;
//			
//		case PRIMARY:
//			courseRegistrationRepository
//				.setPrimaryCourseIdAsAtIndexByCourseRegistrationId(
//					courseId,
//					courseRegistrationRepository
//						.findFirstVacantPrimaryCourseIdIndexByCourseRegistrationId(
//							courseRegistrationId),
//					courseRegistrationId);
//		}
//		
//	}
//
//	@Override
//	public void dropCourse(Integer courseId, Student student) 
//			throws 
//				CourseNotExistsInCatalogException,
//				StudentNotRegisteredForSemesterException, 
//				StudentNotRegisteredForCourseInSemesterException {
//		
//		logger.info("courseId: " + courseId + ", student: " + student);
//		
//		if(!hasRegistrationByStudentIdAndSemester(
//				student.getStudentId(), student.getCurrentSemester())) {
//			throw new StudentNotRegisteredForSemesterException(
//					student.getStudentId(),
//					student.getCurrentSemester());
//		}
//		
//		if(!isStudentRegisteredForCourse(student, courseId)) {
//			throw new StudentNotRegisteredForCourseInSemesterException(student, courseId);
//		}
//		
//		Integer courseRegistrationId = courseRegistrationRepository.findCourseRegistrationIdByStudent(student);
//		CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
//		
//		switch(courseType) {
//		
//		case ALTERNATIVE:
//			courseRegistrationRepository
//				.setAlternativeCourseIdAsNullAtIndexByCourseRegistrationId(
//					courseRegistrationRepository
//						.findAlternativeCourseIdIndexByCourseRegistrationIdForCourse(
//							courseRegistrationId, courseId), 
//					courseRegistrationId);
//			break;
//		
//		case PRIMARY:
//			courseRegistrationRepository
//				.setPrimaryCourseIdAsNullAtIndexByCourseRegistrationId(
//					courseRegistrationRepository
//						.findPrimaryCourseIdIndexByCourseRegistrationIdForCourse(
//							courseRegistrationId, courseId),
//					courseRegistrationId);
//		}
//	}
//
//	@Override
//	public List<Student> getRegisteredStudentsByCourseId(Integer courseId) throws CourseNotExistsInCatalogException {
//		
//		if(!courseService.isCourseExistsInCatalog(courseId)) {
//			throw new CourseNotExistsInCatalogException(courseId);
//		}
//		
//		return courseRegistrationRepository
//					.findAllStudentIdsByCourseId(courseId)
//					.stream()
//					.map(studentId -> studentService.getStudentById(studentId))
//					.collect(Collectors.toList());
//	}
//
//	@Override
//	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId)
//			throws UserNotFoundException {
//		List<Course> courses = courseService.getCoursesAssignedToProfessor(professorId);
//		Map<Integer, List<Student>> courseIdToRegisteredStudentsMap = new HashMap<>();
//		courses
//			.forEach(course -> {
//				CourseRegistration courseRegistration = courseRegistrationRepository
//						.findByCourseIdAndSemesterAndYear(
//								course.getCourseId(), 
//								course.getSemester(),
//								course.getYear());
//				Student student = studentService.getStudentById(courseRegistration.getStudentId());
//				if(!courseIdToRegisteredStudentsMap.containsKey(course.getCourseId())) {
//					courseIdToRegisteredStudentsMap.put(course.getCourseId(), new ArrayList<>());
//				}
//				courseIdToRegisteredStudentsMap.get(course.getCourseId()).add(student);
//			});
//		return courseIdToRegisteredStudentsMap;
//	}
//	
//	@Override
//	public List<CourseRegistration> getCourseRegistrationsByRegistrationStatus(
//			RegistrationStatus registrationStatus){
//		return courseRegistrationRepository
//			.findAllByRegistrationStatus(registrationStatus);
//	}
//	
//	@Override
//	public Boolean updateCourseRegistrationStatusToByRegistrationIds(
//			RegistrationStatus registrationStatus,
//			Set<Integer> courseRegistrationIds){
//		
//		logger.info("registrationStatus: " + registrationStatus.toString());
//		logger.info("courseRegistrationIds: " + courseRegistrationIds);
//		
//		// TODO: Should add this check
////		Optional<Integer> invalidCourseRegistrationId = getFirstInvalidCourseRegistrationId(courseRegistrationIds);
////		if(invalidCourseRegistrationId.isPresent()) {
////			throw new InvalidCourseRegistrationIdArgumentException(invalidCourseRegistrationId.get());
////		}
//		
//		return courseRegistrationRepository
//					.updateRegistrationStatusAsByIdIn(
//						registrationStatus,
//						courseRegistrationIds);
//	}
//	
//	@Override
//	public Boolean updateAllPendingCourseRegistrationsTo(
//			RegistrationStatus registrationStatus) {
//		return courseRegistrationRepository
//					.updateRegistrationStatusAsByIdIn(
//							registrationStatus, 
//							courseRegistrationRepository
//								.findAllByRegistrationStatus(RegistrationStatus.PENDING)
//								.stream()
//								.map(courseRegistration -> courseRegistration.getCourseRegId())
//								.collect(Collectors.toSet()));
//	}
//
//	@Override
//	public List<Student> getRegisteredStudentsByProfessorIdAndCourseId(
//		Integer professorId, Integer courseId)
//			throws 
//				CourseNotExistsInCatalogException,
//				UserNotFoundException, 
//				ProfessorNotAssignedForCourseException {
//		
//		if(null == professorId || null == courseId) {
//			return Collections.emptyList();
//		}
//		
//		if(!userOperation.isUserExistsById(professorId)) {
//			throw new UserNotFoundException(professorId);
//		}
//		
//		if(!courseService.isCourseExistsInCatalog(courseId)) {
//			throw new CourseNotExistsInCatalogException(courseId);
//		}
//		
//		if(!courseService.isProfessorAssignedForCourse(professorId, courseId)) {
//			throw new ProfessorNotAssignedForCourseException(professorId, courseId);
//		}
//		
//		return getRegisteredStudentsByCourseId(courseId);
//	}
//	
//	@Override
//	public Boolean hasRegistrationByStudentIdAndCourseId(
//			Integer studentId, Integer courseId) 
//				throws 
//					UserNotFoundException, 
//					CourseNotExistsInCatalogException {
//		
//		if(!userOperation.isUserExistsById(studentId)) {
//			throw new UserNotFoundException(studentId);
//		}
//		
//		if(!courseService.isCourseExistsInCatalog(courseId)) {
//			throw new CourseNotExistsInCatalogException(courseId);
//		}
//		
//		return courseRegistrationRepository
//				.existsByStudentIdAndCourseId(studentId, courseId);
//	}
//	
//	@Override
//	public Boolean hasRegistrationByStudentIdAndSemester(Integer studentId, Integer semester) {
//		return courseRegistrationRepository.existsByStudentIdAndSemester(studentId, semester);
//	}
//
//	/*************************** Utility Methods ********************************/
//	
//	private Boolean isStudentRegisteredForCourse(Student student, Integer courseId) {
//		return courseRegistrationRepository
//				.existsByStudentIdAndSemesterAndCourseId(
//						student.getStudentId(), 
//						student.getCurrentSemester(), 
//						courseId);
//	}
//	
//	private Boolean isStudentRegisteredForAllAlternativeCourses(Student student) {
//		return courseRegistrationRepository
//				.findFirstVacantAlternativeCourseIdIndexByCourseRegistrationId(
//					courseRegistrationRepository.findCourseRegistrationIdByStudent(student)) == -1;
//	}
//	
//	private Boolean isStudentRegisteredForAllPrimaryCourses(Student student) {
//		return courseRegistrationRepository
//				.findFirstVacantPrimaryCourseIdIndexByCourseRegistrationId(
//					courseRegistrationRepository.findCourseRegistrationIdByStudent(student)) == -1;
//	}	
//}
