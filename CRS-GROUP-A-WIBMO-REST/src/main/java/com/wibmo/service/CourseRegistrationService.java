package com.wibmo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.dto.CourseProfessorDTO;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;

/**
 * 
 */
public interface CourseRegistrationService {

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
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 */
	public List<CourseProfessorDTO> getRegisteredCoursesByStudent(Student student)
			throws StudentNotRegisteredForSemesterException, UserNotFoundException, CourseNotExistsInCatalogException;
	
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
	 * @return 
	 */
	public List<CourseRegistration> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus);
	
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
	 * @return 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws ProfessorNotExistsInSystemException 
	 * @throws ProfessorNotAssignedForCourseException 
	 * @throws ProfessorNotAssignedForCourseException 
	 */
	public List<Student> getRegisteredStudentsByProfessorIdAndCourseId(Integer professorId, Integer courseId)
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException,
				ProfessorNotAssignedForCourseException;

	/**
	 * 
	 * @param studentId
	 * @param courseId
	 * @return
	 * @throws UserNotFoundException 
	 * @throws CourseNotExistsInCatalogException 
	 */
	public Boolean hasRegistrationByStudentIdAndCourseId(
			Integer studentId, Integer courseId) 
				throws 
					UserNotFoundException, 
					CourseNotExistsInCatalogException;

	/**
	 * 
	 * @param studentId
	 * @param semester
	 * @return
	 */
	public Boolean hasRegistrationByStudentIdAndSemester(
			Integer studentId, Integer semester);
	
	
}
