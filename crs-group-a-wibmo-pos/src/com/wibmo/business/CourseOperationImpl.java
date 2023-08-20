package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.dao.CourseDAO;
import com.wibmo.dao.CourseDAOImpl;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotExistsInSystemException;

public class CourseOperationImpl implements CourseOperation {

	private final ProfessorOperation professorOperation;
	
	private final CourseDAO courseDAO;
	
	public CourseOperationImpl(
			ProfessorOperation professorOperation) {
		this.professorOperation = professorOperation;
		courseDAO = new CourseDAOImpl();
	}
	
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
	public void viewAllCourses() {
		courseDAO.viewAllCourse();
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
	public void assignCourseToProfessor(int courseId, int professorId) {
		courseDAO.assignCoursesToProfessor(courseId, professorId);
	}

	@Override
	public void viewCoursesTaughtByProfessor(Professor professor) {
		
		if(null == professor) {
			return;
		}
		
		System.out.print("*** List of Courses Taught:- ***\n"
				+ "\n+--------------------------------------------+\n"
				+ "CourseId |\tCourseTitle\t| CourseType "
				+ "\n+--------------------------------------------+\n");
		
		getCoursesAssignedToProfessor(professor.getProfessorId())
			.forEach(course -> System.out.format(
					"%5d\t | %s \t| %s\n", 
						course.getCourseId(), 
						course.getCourseTitle(),
						course.getCourseType().toString()));
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
