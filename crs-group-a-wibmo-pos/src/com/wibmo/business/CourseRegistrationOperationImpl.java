package com.wibmo.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.bean.Course;
import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
// import com.wibmo.bean.User;
import com.wibmo.dao.CourseRegistrationDAO;
import com.wibmo.dao.CourseRegistrationDAOImpl;
import com.wibmo.enums.RegistrationStatus;

public class CourseRegistrationOperationImpl implements CourseRegistrationOperation {
	
	private final StudentOperation studentOperation;
	
	private final ProfessorOperation professorOperation;
	
	private final CourseOperation courseOperation;
	
	private final CourseRegistrationDAO courseRegistrationDAO;
	
	public CourseRegistrationOperationImpl(
			StudentOperation studentOperation,
			ProfessorOperation professorOperation,
			CourseOperation courseOperation) {
		
		this.studentOperation = studentOperation;
		this.professorOperation = professorOperation;
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
		Map<Integer, Professor> professorIdToProfessorMap = professorOperation.getProfessorIdToProfessorMap(
				courseIdToCourseMap
					.entrySet()
					.stream()
					.map(entry -> entry.getValue().getProfessorId())
					.collect(Collectors.toSet()));
		
		System.out.println("Here are registered courses for Student Id: " + student.getStudentId() + " and semester: " + student.getCurrentSemester());
		System.out.println("+------------------------------------------------------+");
		System.out.println(" CourseId    CourseTitle    Department    ProfessorName ");
		System.out.println("+------------------------------------------------------+");
		courseIdToCourseMap
			.entrySet()
			.stream()
			.map(entry -> entry.getValue())
			.forEach(course -> {
				System.out.format("%5d%15s%15s%15s\n", 
						course.getCourseId(),
						course.getCourseTitle(),
						course.getDepartment(),
						professorIdToProfessorMap.get(course.getProfessorId()).getProfessorName());

			});
	}
	
	@Override
	public RegistrationStatus getRegistrationStatusByStudent(Student student) {
		if(!hasRegistered(student)) {
			// TODO: Move to Exception
			System.out.println("Student Id: " + student.getStudentId() + " is not registered for semester: " + student.getCurrentSemester());
		}
		return courseRegistrationDAO.findRegistrationStatusByStudent(student);
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
	public List<Student> getRegisteredStudentsByCourseId(Integer courseId) {
		return courseRegistrationDAO
					.findAllStudentIdsByCourseId(courseId)
					.stream()
					.map(studentId -> studentOperation.getStudentById(studentId))
					.collect(Collectors.toList());
	}

	@Override
	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId) {
		List<Course> courses = courseOperation.getCoursesAssignedToProfessor(professorId);
		Map<Integer, List<Student>> courseIdToRegisteredStudentsMap = new HashMap<>();
		courses
			.forEach(course -> {
				CourseRegistration courseRegistration = courseRegistrationDAO
						.findByCourseIdAndSemesterAndYear(
								course.getCourseId(), 
								course.getSemester(),
								course.getYear());
				Student student = studentOperation.getStudentById(courseRegistration.getStudentId());
				if(!courseIdToRegisteredStudentsMap.containsKey(course.getCourseId())) {
					courseIdToRegisteredStudentsMap.put(course.getCourseId(), new ArrayList<>());
				}
				courseIdToRegisteredStudentsMap.get(course.getCourseId()).add(student);
			});
		return courseIdToRegisteredStudentsMap;
	}

	public void viewCourseRegistrationByRegistrationStatus(RegistrationStatus regStatus){
		courseRegistrationDAO.viewCourseRegistrationStatus(regStatus);
	}
	public boolean approveRegistrationByRegistrationId(int courseRegId){
		return courseRegistrationDAO.approveRegistrationStatus(courseRegId);
	}
	public boolean rejectRegistrationByRegistrationId(int courseRegId){
		return courseRegistrationDAO.rejectRegistrationStatus(courseRegId);
	}
	
}
