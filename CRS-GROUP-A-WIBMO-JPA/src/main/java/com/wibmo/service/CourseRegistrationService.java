package com.wibmo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.InvalidCourseForCourseTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllCoursesOfTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotEligibleForCourseRegistrationException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;

/**
 * Defines the contracts that to support all required operations
 * on CourseRegistration.
 */
public interface CourseRegistrationService {

	/**
	 * 
	 * @param courseRegistrationRequestDTO
	 * @throws StudentAlreadyRegisteredForSemesterException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 * @throws StudentNotEligibleForCourseRegistrationException
	 * @throws InvalidCourseForCourseTypeException 
	 */
	public void register(CourseRegistrationRequestDTO courseRegistrationRequestDTO) 
				throws 
					StudentAlreadyRegisteredForSemesterException, 
					CourseNotExistsInCatalogException, 
					UserNotFoundException, 
					StudentNotEligibleForCourseRegistrationException,
					InvalidCourseForCourseTypeException;
	
	/**
	 * 
	 * @param studentId
	 * @param semOfStudy
	 * @return 
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 */
	public List<CourseResponseDTO> getRegisteredCoursesByStudentIdAndSemester(
			Integer studentId, Integer semester)
			throws StudentNotRegisteredForSemesterException, UserNotFoundException, CourseNotExistsInCatalogException;
	
	/**
	 * 
	 * @param student
	 * @return
	 * @throws StudentNotRegisteredForSemesterException 
	 */
	public RegistrationStatus getRegistrationStatusByStudentIdAndSemester(Integer studentId, Integer semester) 
			throws StudentNotRegisteredForSemesterException;
	
	/**
	 * 
	 * @param courseId
	 * @param student
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws StudentAlreadyRegisteredForCourseInSemesterException 
	 * @throws StudentAlreadyRegisteredForAllCoursesOfTypeException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 */
	public void addCourse(Integer courseId, Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentAlreadyRegisteredForCourseInSemesterException,
				StudentAlreadyRegisteredForAllCoursesOfTypeException,
				CourseNotExistsInCatalogException, 
				UserNotFoundException;
	
	/**
	 * 
	 * @param courseId
	 * @param student
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws StudentNotRegisteredForCourseInSemesterException 
	 * @throws CourseNotExistsInCatalogException 
	 */
	public void dropCourse(Integer courseId, Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentNotRegisteredForCourseInSemesterException, 
				CourseNotExistsInCatalogException;
	
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
	 * @throws UserNotFoundException 
	 */
	public Boolean hasRegistrationByStudentIdAndSemester(
			Integer studentId, Integer semester) throws UserNotFoundException;
	
	
}
