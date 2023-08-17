package com.wibmo.business;

import java.util.List;
import java.util.Map;

import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotFoundException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;

/**
 * 
 */
public interface CourseRegistrationOperation {

	/**
	 * 
	 * @param primaryCourses
	 * @param alternativeCourses
	 * @param student
	 * @throws StudentAlreadyRegisteredForSemesterException 
	 * @throws CourseNotFoundException 
	 */
	public void register(
			List<Integer> primaryCourses, 
			List<Integer> alternativeCourses, 
			Student student) 
					throws 
						StudentAlreadyRegisteredForSemesterException, 
						CourseNotFoundException;
	
	/**
	 * 
	 * @param studentId
	 * @param semOfStudy
	 * @throws StudentNotRegisteredForSemesterException 
	 */
	public void viewRegisteredCoursesByStudent(Student student)
			throws StudentNotRegisteredForSemesterException;
	
	/**
	 * 
	 * @param student
	 * @return
	 * @throws StudentNotRegisteredForSemesterException 
	 */
	public RegistrationStatus getRegistrationStatusByStudent(Student student) 
			throws StudentNotRegisteredForSemesterException;
	
	/**
	 * 
	 * @param courseId
	 * @param student
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws StudentAlreadyRegisteredForCourseInSemesterException 
	 * @throws StudentAlreadyRegisteredForAllAlternativeCoursesException 
	 * @throws StudentAlreadyRegisteredForAllPrimaryCoursesException 
	 * @throws CourseNotFoundException 
	 */
	public void addCourse(Integer courseId, Student student) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentAlreadyRegisteredForCourseInSemesterException, 
				StudentAlreadyRegisteredForAllAlternativeCoursesException,
				StudentAlreadyRegisteredForAllPrimaryCoursesException,
				CourseNotFoundException;
	
	/**
	 * 
	 * @param courseId
	 * @param student
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws StudentNotRegisteredForCourseInSemesterException 
	 */
	public void dropCourse(Integer courseId, Student student) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentNotRegisteredForCourseInSemesterException;
	
	/**
	 * 
	 * @param courseId
	 */
	public List<Student> getRegisteredStudentsByCourseId(Integer courseId);
	
	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId);

}
