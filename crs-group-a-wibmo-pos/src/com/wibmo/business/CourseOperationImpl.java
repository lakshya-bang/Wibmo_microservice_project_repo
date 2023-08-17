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

}
