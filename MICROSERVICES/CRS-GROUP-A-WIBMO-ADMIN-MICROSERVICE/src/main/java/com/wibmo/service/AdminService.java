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
	 * 
	 * @return
	 */
	public List<Admin> getAllAdmins();
	
	/**
	 * Adds the new admin into the Database
	 * 
	 * @param admin
	 */
	public void add(Admin admin);
	
	
	public List<CourseResponseDTO> getRegisteredCoursesByStudentIdAndSemester(
			Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				UserNotFoundException,
				CourseNotExistsInCatalogException;
	
	public RegistrationStatus getRegistrationStatusByStudentIdAndSemester(
			Integer studentId, Integer semester) 
			throws StudentNotRegisteredForSemesterException;

	public List<CourseRegistrationResponseDTO> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus);
	
	public Boolean updateCourseRegistrationStatusToByRegistrationIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> courseRegistrationIds) 
					throws CannotApproveCourseRegistrationPaymentPendingException;
	
	public Boolean updateAllPendingCourseRegistrationsTo(
			RegistrationStatus registrationStatus) 
					throws CannotApproveCourseRegistrationPaymentPendingException;
	
	public Boolean add(CourseRequestDTO courseRequestDTO);
	
	public Boolean addAll(Collection<CourseRequestDTO> courseRequestDTOs);
	
	public Boolean removeCourseById(Integer courseId) 
			throws 
				CourseNotExistsInCatalogException, 
				CannotDropCourseAssignedToProfessorException ;
	
	public void assignCourseToProfessor(Integer courseId, Integer professorId) 
			throws 
				CourseNotExistsInCatalogException, 
				UserNotFoundException;
	
	public List<Integer> getRegisteredCourseIdsByRegistrationId(Integer registrationId);
}
