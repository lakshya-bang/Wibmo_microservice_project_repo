package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.converter.CourseConverter;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRepository;

public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseConverter courseConverter;
	
	@Override
	public CourseResponseDTO getCourseById(Integer courseId) {
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		if(!courseOptional.isPresent()) {
			return null;
		}
		Course course = courseOptional.get();
		Professor professor = null;
		if(null != course.getProfessorId()) {
			professor = professorService.getProfessorById(course.getProfessorId());
		}
		return courseConverter.convert(course, professor);
	}
	
	@Override
	public List<CourseResponseDTO> getAllCourses() {
		List<Course> courses = courseRepository.findAll();
		Map<Integer, Professor> professorIdToProfessorMap = professorService
				.getProfessorIdToProfessorMap(
						courses
							.stream()
							.map(Course::getProfessorId)
							.filter(Objects::nonNull)
							.collect(Collectors.toSet()));
		
		return courseConverter.convertAll(courses, professorIdToProfessorMap);
	}
	
	@Override
	public List<CourseResponseDTO> getCourseDetailsByIds(Collection<Integer> courseIds) {
		List<Course> courses = courseRepository.findAllByCourseIdIn(courseIds);
		Map<Integer, Professor> professorIdToProfessorMap = professorService
				.getProfessorIdToProfessorMap(
						courses
							.stream()
							.map(Course::getProfessorId)
							.filter(Objects::nonNull)
							.collect(Collectors.toSet()));
		
		return courseConverter.convertAll(courses, professorIdToProfessorMap);
	}
	
	// TODO: This implementation can be moved to Join query in Database
	@Override
	public List<CourseResponseDTO> getCourseDetailsBySemester(Integer semester) {
		List<Course> courses = courseRepository.findAllBySemester(semester);
		Map<Integer, Professor> professorIdToProfessorMap = professorService
				.getProfessorIdToProfessorMap(
						courses
							.stream()
							.map(course -> course.getProfessorId())
							.filter(Objects::nonNull)
							.collect(Collectors.toSet()));
		
		return courseConverter.convertAll(courses, professorIdToProfessorMap);
	}

	@Override
	public Map<Integer, Course> getCourseIdToCourseMap(Collection<Integer> courseIds) {
		return courseRepository
				.findAllByCourseIdIn(courseIds)
				.stream()
				.collect(Collectors.toMap(
						Course::getCourseId,
						Function.identity()));
	}

	// TODO: Should return a CourseLiteDTO
	@Override
	public List<Course> getCoursesAssignedToProfessor(Integer professorId) 
			throws UserNotFoundException {
		
		if(!professorService.isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		return courseRepository
				.findAllByProfessorId(professorId);
	}
	
	@Override
	public CourseType getCourseTypeByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException {
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
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
		
		if(!professorService.isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		// TODO: If professor registration is not approved,
		// we cannot assign course to him/her.
		
		Course course = courseOptional.get();
		
		course.setProfessorId(professorId);
		
		courseRepository.save(course);
	}

	@Override
	public Boolean isProfessorAssignedForCourse(Integer professorId, Integer courseId)
		throws 
			UserNotFoundException, 
			CourseNotExistsInCatalogException {

		if(null == professorId || null == courseId) {
			return false;
		}
		
		if(!professorService.isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(!courseOptional.isPresent()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		Course course = courseOptional.get();
		
		return null != course.getProfessorId()
				&& professorId.equals(course.getProfessorId());
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

}
