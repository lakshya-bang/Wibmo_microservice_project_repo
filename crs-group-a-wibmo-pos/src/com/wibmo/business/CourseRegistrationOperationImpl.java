package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.bean.Course;
import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dao.CourseRegistrationDAO;
import com.wibmo.dao.CourseRegistrationDAOImpl;
import com.wibmo.enums.RegistrationStatus;

public class CourseRegistrationOperationImpl implements CourseRegistrationOperation {

	private final UserOperation userOperation;
	
	private final CourseOperation courseOperation;
	
	private final CourseRegistrationDAO courseRegistrationDAO;
	
	public CourseRegistrationOperationImpl(
			UserOperation userOperation,
			CourseOperation courseOperation) {
		this.userOperation = userOperation;
		this.courseOperation = courseOperation;
		courseRegistrationDAO = new CourseRegistrationDAOImpl();
	}
	
	@Override
	public void register(List<Integer> primaryCourses, List<Integer> alternativeCourses, Student student) {
		
		// TODO: Check if Registration is Enabled by Admin
		
		CourseRegistration courseRegistration = new CourseRegistration();
		courseRegistration.setStudentId(student.getStudentId());
		courseRegistration.setSemester(student.getCurrentSemester());
		courseRegistration.setPrimaryCourse1Id(primaryCourses.get(0));
		courseRegistration.setPrimaryCourse2Id(primaryCourses.get(1));
		courseRegistration.setPrimaryCourse3Id(primaryCourses.get(2));
		courseRegistration.setPrimaryCourse4Id(primaryCourses.get(3));
		courseRegistration.setAlternativeCourse1Id(alternativeCourses.get(0));
		courseRegistration.setAlternativeCourse2Id(alternativeCourses.get(1));
		// Admin will approve / reject the registrations later
		courseRegistration.setRegistrationStatus(RegistrationStatus.PENDING);
		
		courseRegistrationDAO.save(courseRegistration);
		
	}

	@Override
	public void viewRegisteredCoursesByStudent(Student student) {
		if(!hasRegistered(student)) {
			// TODO: Move to Exception
			System.out.println("Student Id: " + student.getStudentId() + " is not registered for semester: " + student.getCurrentSemester());
		}
		
		CourseRegistration courseRegistration = courseRegistrationDAO.findByStudent(student);
		Map<Integer, Course> courseIdToCourseMap = courseOperation
				.getCourseIdToCourseMap(Set.of(
						courseRegistration.getPrimaryCourse1Id(),
						courseRegistration.getPrimaryCourse2Id(),
						courseRegistration.getPrimaryCourse3Id(),
						courseRegistration.getPrimaryCourse4Id(),
						courseRegistration.getAlternativeCourse1Id(),
						courseRegistration.getAlternativeCourse2Id()));
		Map<Integer, User> userIdToUserMap = userOperation.getUserIdToUserMap(
				courseIdToCourseMap
					.entrySet()
					.stream()
					.map(entry -> entry.getValue().getProfessorId())
					.collect(Collectors.toSet()));
		
		System.out.println("Here are registered courses for Student Id: " + student.getStudentId() + " and semester: " + student.getCurrentSemester());
		System.out.println("+------------------------------------------------------------------------+");
		System.out.println(" CourseId    CourseTitle    Department    ProfessorName    ProfessorEmail");
		System.out.println("+------------------------------------------------------------------------+");
		courseIdToCourseMap
			.entrySet()
			.stream()
			.map(entry -> entry.getValue())
			.forEach(course -> {
				System.out.format("%5d%10s%10s%10s%10s\n", 
						course.getCourseId(),
						course.getName(),
						course.getDepartment(),
						userIdToUserMap.get(course.getProfessorId()).getUserName(),
						userIdToUserMap.get(course.getProfessorId()).getUserEmail());	
			});
	}
	
	@Override
	public RegistrationStatus getRegistrationStatusByStudent(Student student) {
		if(!hasRegistered(student)) {
			// TODO: Move to Exception
			System.out.println("Student Id: " + student.getStudentId() + " is not registered for semester: " + student.getCurrentSemester());
		}
		return RegistrationStatus.valueOf(courseRegistrationDAO.findRegistrationStatusByStudent(student));
	}
	

	@Override
	public void addCourse(Integer courseId, Student student) {
		
		// TODO: Check if Add / Drop is Enabled by Admin
		
		throw new UnsupportedOperationException();
	}

	@Override
	public void dropCourse(Integer courseId, Student student) {
		
		// TODO: Check if Add / Drop is Enabled by Admin
		
		throw new UnsupportedOperationException();
	}

	private boolean hasRegistered(Student student) {
		return courseRegistrationDAO.existsByStudent(student);
	}

	@Override
	public void getRegisteredStudentsByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getRegisteredStudentsByCourseId'");
	}
}
