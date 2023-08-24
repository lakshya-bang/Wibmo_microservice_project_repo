package com.wibmo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.dao.CourseDAOImpl;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private UserServiceImpl userOperation;
	
	@Autowired
	private ProfessorServiceImpl professorOperation;
	
	@Autowired
	private CourseDAOImpl courseDAO;
	
	// TODO: This implementation can be moved to Join query in Database
	@Override
	public void viewCourseDetailsBySemester(Integer currentSemester) {
		List<Course> courses = courseDAO.findAllBySemester(currentSemester);
		Map<Integer, Professor> professorIdToProfessorMap = professorOperation
				.getProfessorIdToProfessorMap(
						courses
							.stream()
							.map(course -> course.getProfessorId())
							.filter(professorId -> professorId != null)
							.collect(Collectors.toSet()));
		
		System.out.println("List of applicable Courses for Semeseter: " + currentSemester);
		System.out.println(" CourseID  | \t CourseTitle \t|  Course Type  | Seats |   \t   ProfessorName   \t| \tDepartment ");
		System.out.println("+----------------------------------------------------------------------------------------------------------------------------+");
		courses
			.forEach(
				course -> System.out.format(
						"|    %d\t| %s\t| %s\t| %d\t| %s \t| %s |\n", 
						// "%5d%15s%16d%20s%13s\n", 
							course.getCourseId(),
							course.getCourseTitle(),
							course.getCourseType().toString(),
							course.getNoOfSeats(),
							null != course.getProfessorId()
								? professorIdToProfessorMap.get(course.getProfessorId()).getProfessorName()
								: "NULL",
							null != course.getProfessorId()
								? professorIdToProfessorMap.get(course.getProfessorId()).getDepartment()
								: "NULL"));
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
	public List<Course> getCoursesAssignedToProfessor(Integer professorId) 
			throws UserNotFoundException {
		
		if(!userOperation.isUserExistsById(professorId)) {
			throw new UserNotFoundException(professorId);
		}
		
		return courseDAO
				.findAllByProfessorId(professorId);
	}
	
	@Override
	public CourseType getCourseTypeByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException {
		
		if(!isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		return courseDAO
				.findCourseTypeByCourseId(courseId);
	}

	@Override
	public Boolean isCourseExistsInCatalog(Integer courseId) {
		return courseDAO
				.existsByCourseId(courseId);
	}

	@Override
	public List<Course> getAllCourses() {
		return courseDAO.findAll();
	}

	@Override
	public Boolean add(Course course) {
		
		// TODO: Move to Validator
		if(null == course || null != course.getCourseId()) {
			return Boolean.FALSE;
		}
		
		return courseDAO.save(course); 
	}

	@Override
	public Boolean removeCourseById(Integer courseId) 
			throws 
				CourseNotExistsInCatalogException, 
				CannotDropCourseAssignedToProfessorException {
		
		if(!isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		Integer professorId = courseDAO.findProfessorIdByCourseId(courseId);
		
		if(null != professorId) {
			throw new CannotDropCourseAssignedToProfessorException(courseId, professorId);
		}
		
		return courseDAO.deleteCourseById(courseId);
	}

	@Override
	public void assignCourseToProfessor(Integer courseId, Integer professorId) 
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException {
		
		if(!isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		if(!userOperation.isUserExistsById(professorId)) {
			throw new UserNotFoundException(professorId);
		}
		
		courseDAO.setProfessorIdAsWhereCourseIdIs(professorId, courseId);
	}

	@Override
	public void viewCoursesTaughtByProfessor(Professor professor) {
		
		// TODO: Move to Validator
		if(null == professor || null == professor.getProfessorId()) {
			return;
		}
		
		System.out.print("*** List of Courses Taught:- ***\n"
				+ "\n+--------------------------------------------+\n"
				+ "CourseId |\tCourseTitle\t| CourseType "
				+ "\n+--------------------------------------------+\n");
		
		try {
			getCoursesAssignedToProfessor(professor.getProfessorId())
				.forEach(course -> System.out.format(
						"%5d\t | %s \t| %s\n", 
							course.getCourseId(), 
							course.getCourseTitle(),
							course.getCourseType().toString()));
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	@Override
	public Boolean isProfessorAssignedForCourse(Integer professorId, Integer courseId)
		throws 
			UserNotFoundException, 
			CourseNotExistsInCatalogException {

		if(null == professorId || null == courseId) {
			return false;
		}
		
		if(!userOperation.isUserExistsById(professorId)) {
			throw new UserNotFoundException(professorId);
		}
		
		if(!isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		return null != professorId
				&& professorId.equals(
						courseDAO.findProfessorIdByCourseId(courseId));
	}

	/************************** Utility Methods **************************/
	
	private boolean isCourseAssignedToAnyProfessor(Integer courseId) {
		return courseDAO.findProfessorIdByCourseId(courseId) != null;
	}
	
	
}
