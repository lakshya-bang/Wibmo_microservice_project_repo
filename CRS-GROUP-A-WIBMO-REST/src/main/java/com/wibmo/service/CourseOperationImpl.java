package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.dao.CourseDAO;
import com.wibmo.dao.CourseDAOImpl;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotExistsInSystemException;


@Component
@Service
public class CourseOperationImpl implements CourseOperation {

	private final ProfessorOperation professorOperation;
	@Autowired
	private CourseDAOImpl courseDAO;
	
	@Autowired
	public CourseOperationImpl(ProfessorOperation professorOperation) {
		this.professorOperation = professorOperation;
	}
	
	// TODO: This implementation can be moved to Join query in Database
	@Override
	public List<Course> viewCourseDetailsBySemester(Integer currentSemester) {
		return courseDAO.findAllBySemester(currentSemester);
		
	}

	@Override
	public Map<Integer, Course> getCourseIdToCourseMap(Set<Integer> courseIds) {
		return courseDAO
				.findAllByCourseIdIn(courseIds)
				.stream()
				.collect(Collectors.toMap(
						Course::getCourseId,
						Function.identity()));
	}

	@Override
	public List<Course> getCoursesAssignedToProfessor(Integer professorId) {
		return courseDAO
				.findAllByProfessorId(professorId);
	}
	
	@Override
	public CourseType getCourseTypeByCourseId(Integer courseId) {
		return courseDAO
				.findCourseTypeByCourseId(courseId);
	}

	@Override
	public Boolean isCourseExistsInCatalog(Integer courseId) {
		return courseDAO
				.existsByCourseId(courseId);
	}

	/*
	 * added crs admin menu method
	 */
	@Override
	public List<Course> viewAllCourses() {
		return courseDAO.viewAllCourse();
	}

	@Override
	public boolean addCourse(Course course) {
		return courseDAO.saveCourse(course); 
	}

	@Override
	public boolean removeCourseById(int courseId) {
		return courseDAO.deleteCourse(courseId);
	}

	@Override
	public boolean assignCourseToProfessor(int courseId, int professorId) {
		return courseDAO.assignCoursesToProfessor(courseId, professorId);
	}

	@Override
	public List<Course> viewCoursesTaughtByProfessorId(Integer professorId) {
		
		
		System.out.print("*** List of Courses Taught:- ***\n"
				+ "\n+--------------------------------------------+\n"
				+ "CourseId |\tCourseTitle\t| CourseType "
				+ "\n+--------------------------------------------+\n");
		
		return getCoursesAssignedToProfessor(professorId);
			
	}

	@Override
	public Boolean isProfessorAssignedForCourse(Integer professorId, Integer courseId)
		throws 
			ProfessorNotExistsInSystemException, 
			CourseNotExistsInCatalogException {

		if(null == professorId || null == courseId) {
			return false;
		}
		
		if(!professorOperation.isProfessorExistsById(professorId)) {
			throw new ProfessorNotExistsInSystemException(professorId);
		}
		
		if(!isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		return null != professorId
				&& professorId.equals(
						courseDAO.findProfessorIdByCourseId(courseId));
	}
	
	
	
}
