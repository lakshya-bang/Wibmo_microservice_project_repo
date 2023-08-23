package com.wibmo.service;

import java.util.List;
import java.util.Map;

import com.wibmo.bean.Student;
import com.wibmo.dto.RegisteredCourse;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotExistsInSystemException;
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
	 * @throws CourseNotExistsInCatalogException 
	 */
	public void register(
			List<Integer> primaryCourses, 
			List<Integer> alternativeCourses, 
			Student student) 
					throws 
						StudentAlreadyRegisteredForSemesterException, 
						CourseNotExistsInCatalogException;
	
	/**
	 * 
	 * @param studentId
	 * @param semOfStudy
	 * @return 
	 * @throws StudentNotRegisteredForSemesterException 
	 */
	public List<RegisteredCourse> viewRegisteredCoursesByStudent(Student student)
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
	 * @throws CourseNotExistsInCatalogException 
	 */
	public void addCourse(Integer courseId, Student student) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentAlreadyRegisteredForCourseInSemesterException, 
				StudentAlreadyRegisteredForAllAlternativeCoursesException,
				StudentAlreadyRegisteredForAllPrimaryCoursesException,
				CourseNotExistsInCatalogException;
	
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
	 * @throws CourseNotExistsInCatalogException 
	 */
	public List<Student> getRegisteredStudentsByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException;
	
	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId);

	/**
	 * To add
	 */
	public void viewCourseRegistrationByRegistrationStatus(RegistrationStatus regStatus);
	public boolean approveRegistrationByRegistrationId(int courseRegId);
	public boolean rejectRegistrationByRegistrationId(int courseRegId);

	/**
	 * 
	 * @param professorId
	 * @param courseId
	 * @throws CourseNotExistsInCatalogException 
	 * @throws ProfessorNotExistsInSystemException 
	 * @throws ProfessorNotAssignedForCourseException 
	 * @throws com.wibmo.exception.ProfessorNotAssignedForCourseException 
	 */
	public void viewRegisteredStudentsByProfessorIdAndCourseId(Integer professorId, Integer courseId)
			throws 
				CourseNotExistsInCatalogException, 
				ProfessorNotExistsInSystemException,
				com.wibmo.exception.ProfessorNotAssignedForCourseException;
		
}
