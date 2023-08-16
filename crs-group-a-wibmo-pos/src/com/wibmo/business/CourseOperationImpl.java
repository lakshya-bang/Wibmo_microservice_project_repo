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
							.collect(Collectors.toSet()));
		
		System.out.println("List of applicable Courses for Semeseter: " + currentSemester);
		System.out.println("CourseID    CourseTitle    AvailableSeats     ProfessorName   Department");
		courses.forEach(
				course -> System.out.format(
						"%5d%10s%10d%10s%10s", 
							course.getCourseId(),
							course.getCourseTitle(),
							course.getNoOfSeats(),
							professorIdToProfessorMap.get(course.getProfessorId()).getProfessorName(),
							professorIdToProfessorMap.get(course.getProfessorId()).getDepartment()));
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
<<<<<<< HEAD
	public List<Course> getCoursesAssignedToProfessor(Integer professorId) {
		return courseDAO
				.findAllByProfessorId(professorId);
=======
	public List<Course> viewCoursesTaughtByProfessorId(Integer professorId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'viewCoursesTaughtByProfessorId'");
>>>>>>> da2e123451beb658645848ce71b911e36de428ac
	}

}
