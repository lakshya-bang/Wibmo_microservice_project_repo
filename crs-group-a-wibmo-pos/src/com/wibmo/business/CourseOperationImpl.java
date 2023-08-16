package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.bean.Course;
import com.wibmo.bean.Professor;
import com.wibmo.bean.User;
import com.wibmo.dao.CourseDAO;
import com.wibmo.dao.CourseDAOImpl;

public class CourseOperationImpl implements CourseOperation {

	private final UserOperation userOperation;
	private final ProfessorOperation professorOperation;
	
	private final CourseDAO courseDAO;
	
	public CourseOperationImpl(
			UserOperation userOperation,
			ProfessorOperation professorOperation) {
		this.userOperation = userOperation;
		this.professorOperation = professorOperation;
		courseDAO = new CourseDAOImpl();
	}
	
	// TODO: This implementation can be moved to Join query in Database
	@Override
	public void viewCourseDetailsBySemester(Integer currentSemester) {
		List<Course> courses = courseDAO.findAllBySemester(currentSemester);
		Set<Integer> userIds = courses
							.stream()
							.map(course -> course.getProfessorId())
							.collect(Collectors.toSet());
		Map<Integer, User> userIdToUserMap = userOperation.getUserIdToUserMap(userIds);
		Map<Integer, Professor> userIdToProfessorMap = professorOperation.getProfessorIdToProfessorMap(userIds);
		
		System.out.println("List of applicable Courses for Semeseter: " + currentSemester);
		System.out.println("CourseID    CourseTitle    AvailableSeats     ProfessorName   ProfessorEmail    Department");
		courses.forEach(
				course -> System.out.format(
						"%5d%10s%10d%10s%10s%10s", 
							course.getCourseId(),
							course.getName(),
							course.getNoOfSeats(),
							userIdToUserMap.get(course.getProfessorId()).getUserName(),
							userIdToUserMap.get(course.getProfessorId()).getUserEmail(),
							userIdToProfessorMap.get(course.getProfessorId()).getDepartment()));
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

}
