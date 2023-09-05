package com.wibmo.service;

import java.util.Collection;
import java.util.List;

import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Admin;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CannotApproveCourseRegistrationPaymentPendingException;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;

/**
 * Defines the contracts to support all required Admin operations
 */
public interface AdminService {
	
	/**
	 * Fetches the Admin by the given Id
	 * 
	 * @param integer
	 * @return
	 */
	public Admin getAdminById(Integer integer);
	
	/**
	 * Fetches all the admins
	 * @return
	 */
	public List<Admin> getAllAdmins();
	
	/**
	 * Adds the new admin into the Database
	 * 
	 * @param admin
	 */
	public void add(Admin admin);
	
	/**
	 * Fetches Registered Courses based on given studentId and Semester
	 * @param studentId
	 * @param semester
	 * @return
	 * @throws StudentNotRegisteredForSemesterException
	 * @throws UserNotFoundException
	 * @throws CourseNotExistsInCatalogException
	 */
	public List<CourseResponseDTO> getRegisteredCoursesByStudentIdAndSemester(
			Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				UserNotFoundException,
				CourseNotExistsInCatalogException;
	/**
	 * Fetches Registration Status based on given studentId and Semester
	 * @param studentId
	 * @param semester
	 * @return
	 * @throws StudentNotRegisteredForSemesterException
	 */
	public RegistrationStatus getRegistrationStatusByStudentIdAndSemester(
			Integer studentId, Integer semester) 
			throws StudentNotRegisteredForSemesterException;

	/**
	 * Fetches all the Course Registration based on given Registration Status
	 * @param registrationStatus
	 * @return
	 */
	public List<CourseRegistrationResponseDTO> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus);
	
	/**
	 * Updates the Course Registration Status based on given Registration Ids
	 * @param registrationStatus
	 * @param courseRegistrationIds
	 * @return
	 * @throws CannotApproveCourseRegistrationPaymentPendingException
	 */
	public Boolean updateCourseRegistrationStatusToByRegistrationIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> courseRegistrationIds) 
					throws CannotApproveCourseRegistrationPaymentPendingException;
	
	/**
	 * Updates all the pending Course Registrations to Approved 
	 * @param registrationStatus
	 * @return
	 * @throws CannotApproveCourseRegistrationPaymentPendingException
	 */
	public Boolean updateAllPendingCourseRegistrationsTo(
			RegistrationStatus registrationStatus) 
					throws CannotApproveCourseRegistrationPaymentPendingException;
	
	/**
	 * adds the course
	 * @param courseRequestDTO
	 * @return
	 */
	public Boolean add(CourseRequestDTO courseRequestDTO);
	
	/**
	 * adds a list of courses
	 * @param courseRequestDTOs
	 * @return
	 */
	public Boolean addAll(Collection<CourseRequestDTO> courseRequestDTOs);
	
	/**
	 * Removes course based on given CourseId
	 * @param courseId
	 * @return
	 * @throws CourseNotExistsInCatalogException
	 * @throws CannotDropCourseAssignedToProfessorException
	 */
	public Boolean removeCourseById(Integer courseId) 
			throws 
				CourseNotExistsInCatalogException, 
				CannotDropCourseAssignedToProfessorException ;
	
	/**
	 * Assigns the course to Professor
	 * @param courseId
	 * @param professorId
	 * @throws CourseNotExistsInCatalogException
	 * @throws UserNotFoundException
	 */
	public void assignCourseToProfessor(Integer courseId, Integer professorId) 
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException;
	
	/**
	 * Fetches registered Courses based on given Registration Id.
	 * @param registrationId
	 * @return
	 */
	public List<Integer> getRegisteredCourseIdsByRegistrationId(Integer registrationId);
	
	/**
	 * Updates the Account Registration Status which is given by Admin
	 * @param registrationStatus
	 * @param userIds
	 * @return
	 * @throws UserNotFoundException
	 */
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> userIds) 
					throws UserNotFoundException;

	/**
	 * updates all Account registration Status 
	 * @param registrationStatus
	 * @return
	 */
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus) ;
}
