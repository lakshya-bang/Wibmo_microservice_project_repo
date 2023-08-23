package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.ProfessorNotAssignedForCourseException;

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
	 * @throws CourseNotExistsInCatalogException 
	 */
	public void dropCourse(Integer courseId, Student student) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentNotRegisteredForCourseInSemesterException, CourseNotExistsInCatalogException;
	
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
	 * @throws UserNotFoundException 
	 */
	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId) 
			throws UserNotFoundException;

	/**
	 * 
	 * @param regStatus
	 */
	public void viewCourseRegistrationByRegistrationStatus(RegistrationStatus regStatus);
	
	/**
	 * 
	 * @param courseRegId
	 * @return
	 */
	public Boolean updateCourseRegistrationStatusToByRegistrationIds(
			RegistrationStatus registrationStatus,
			Set<Integer> courseRegistrationIds);

	/**
	 * 
	 * @return
	 */
	public Boolean updateAllPendingCourseRegistrationsTo(
			RegistrationStatus registrationStatus);
	
	/**
	 * 
	 * @param professorId
	 * @param courseId
	 * @throws CourseNotExistsInCatalogException 
	 * @throws ProfessorNotExistsInSystemException 
	 * @throws ProfessorNotAssignedForCourseException 
	 */
	public void viewRegisteredStudentsByProfessorIdAndCourseId(Integer professorId, Integer courseId)
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException,
				ProfessorNotAssignedForCourseException;
		
}
