package com.wibmo.business;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.bean.Student;
import com.wibmo.dao.CourseRegistrationDAO;
import com.wibmo.dao.CourseRegistrationDAOImpl;
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

/**
 * 
 */
public class CourseRegistrationOperationImpl implements CourseRegistrationOperation {

	private StudentOperation studentOperation;
	
	private CourseOperation courseOperation;
	
	private CourseCatalogueOperation courseCatalogueOperation;
	
	private CourseRegistrationDAO courseRegistrationDAO;
	
	public CourseRegistrationOperationImpl(
			StudentOperation studentOperation, 
			CourseOperation courseOperation,
			CourseCatalogueOperation courseCatalogueOperation) {
		
		this.studentOperation = studentOperation;
		this.courseOperation = courseOperation;
		courseRegistrationDAO = new CourseRegistrationDAOImpl();
	}
	
	@Override
	public void register(Long studentId, Set<Long> courseIds) throws 
		StudentNotFoundException, 
		CoursesNotAvailableForRegistrationException, 
		StudentAlreadyRegisteredForSemesterException {
		
		Optional<Student> student = studentOperation.getStudentById(studentId);
		
		if(!student.isPresent()) {
			throw new StudentNotFoundException(studentId);
		}
		
		Integer semOfStudy = student.get().getCurrentSemester();
		
		if(Boolean.TRUE.equals(getRegistrationStatusByStudentIdAndSemOfStudy(
				studentId, semOfStudy))) {
			throw new StudentAlreadyRegisteredForSemesterException(studentId, semOfStudy);
		}
		
		Set<Long> courseIdsWithNoVacancy = courseCatalogueOperation
				.getCourseIdToVacantSeatsMapping(courseIds)
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue() == 0)
				.map(entry -> entry.getKey())
				.collect(Collectors.toSet());
			
		if(!courseIdsWithNoVacancy.isEmpty()) {
			throw new CoursesNotAvailableForRegistrationException(courseIdsWithNoVacancy);
		}
		
		/**
		 * TODO: should be synchronized
		 */
		courseCatalogueOperation.reduceSeatsByCourseIdsAndSeatCount(courseIds, 1);
		courseRegistrationDAO.save(
				studentId,
				semOfStudy,
				courseIds);
	}

	@Override
	public Boolean getRegistrationStatusByStudentIdAndSemOfStudy(Long studentId, Integer semOfStudy) {
		return courseRegistrationDAO.existsByStudentIdAndSemOfStudy(studentId, semOfStudy);
	}

	@Override
	public Set<Long> getRegisteredCourseIdsByStudentIdAndSemOfStudy(Long studentId, Integer semOfStudy) {
		return courseRegistrationDAO.findCourseIdsByStudentIdAndSemOfStudy(studentId, semOfStudy);
	}

}
