package com.wibmo.business;

import com.wibmo.bean.CourseCatalogue;
import com.wibmo.dao.CourseCatalogueDAO;
import com.wibmo.dao.FakeCourseCatalogueDAOImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 */
public class CourseCatalogueOperationImpl implements CourseCatalogueOperation {

	CourseCatalogueDAO courseCatalogueDAO = new FakeCourseCatalogueDAOImpl();
	
	@Override
	public void viewCourseCatalogue() {
		courseCatalogueDAO
			.findAll()
			.forEach(System.out::println);
	}
	
	@Override
	public Map<Long, Integer> getCourseIdToVacantSeatsMapping(Set<Long> courseIds) {
		return courseCatalogueDAO
			.findAllByCourseIdIn(courseIds)
			.stream()
			.collect(Collectors.toMap(
					CourseCatalogue::getCourseId, 
					CourseCatalogue::getAvailableSeats));
	}

	@Override
	public void increaseSeatsBy(Long courseId, Integer extraSeats) {
		// TODO Throw exception if course not exist
	}

	@Override
	public void decreaseSeatsBy(Long courseId, Integer extraSeats) {
		// TODO Throw exception if course not exist
		
	}

	@Override
	public void dropCourse(Long courseId) {
		// TODO Throw exception if course not exist
		
	}

	@Override
	public void addCourse(Long courseId, Long professorId, Integer availableSeats) {
		// TODO DO NOTHING if course already exist.
		// TODO Throw exception if professor not exist
		// TODO Throw exception if availableSeats < 1
	}

	@Override
	public void updateAssignedProfessor(Long courseId, Long professorId) {
		// TODO Throw exception if course not exist
		// TODO Throw exception if professor not exist
		
	}

}
