/**
 * 
 */
package com.wibmo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wibmo.dto.AddDropCourseDTO;
import com.wibmo.dto.CourseIdProfessorIdDTO;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Admin;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CannotApproveCourseRegistrationPaymentPendingException;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.AdminService;
import com.wibmo.service.AdminServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin
//@PreAuthorize("hasAuthority('Role.ADMIN')")
public class AdminController {

	@Autowired
	private AdminServiceImpl adminService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get/{id}")
	public ResponseEntity getAdmin(@PathVariable("id") Integer id) {
		Admin admin = adminService.getAdminById(id);
		if (admin == null) {
			return new ResponseEntity("No Admin found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(admin, HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get-all")
	public ResponseEntity getAllAdmins() {
		return new ResponseEntity(adminService.getAllAdmins(), HttpStatus.OK);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/course-registration/details/{studentId}/{semester}")
	public ResponseEntity viewRegistrationDetailsByStudentId(
			@PathVariable(value = "studentId") Integer studentId, 
			@PathVariable(value = "semester") Integer semester) {
		try {
			List<CourseResponseDTO> courseRegistrations = adminService
					.getRegisteredCoursesByStudentIdAndSemester(studentId, semester);
			return new ResponseEntity(courseRegistrations,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/course-registration/status/{studentId}/{semester}")
	public ResponseEntity viewRegistrationStatusByStudentId(
			@PathVariable(value = "studentId") Integer studentId,
			@PathVariable(value = "semester") Integer semester) {
		try {
			RegistrationStatus registrationStatus = adminService
					.getRegistrationStatusByStudentIdAndSemester(studentId, semester);
			return new ResponseEntity(registrationStatus.toString(),HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/course-registration/pending")
	public ResponseEntity viewPendingCourseRegistrations() {
		return new ResponseEntity(
				adminService
					.getCourseRegistrationsByRegistrationStatus(
						RegistrationStatus.PENDING),
				HttpStatus.OK);
	}
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/course-registration/approve")
	public ResponseEntity approveCourseRegistrationByIds(
			@RequestBody Set<Integer> courseRegistrationIds, @RequestHeader(value="Authorization") String jwt) {
		try {
			adminService
			.updateCourseRegistrationStatusToByRegistrationIds(
				RegistrationStatus.APPROVED, 
				courseRegistrationIds);
			return adminService.SendApproveOrRejectNotification(jwt.substring(7));
		} catch (CannotApproveCourseRegistrationPaymentPendingException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/course-registration/approve-all")
	public ResponseEntity approveAllCourseRegistrations(@RequestHeader(value="Authorization") String jwt) {
		try {
			adminService.updateAllPendingCourseRegistrationsTo(RegistrationStatus.APPROVED);
			
			return adminService.SendApproveOrRejectNotification(jwt.substring(7));
		} catch (CannotApproveCourseRegistrationPaymentPendingException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/course-registration/reject")
	public ResponseEntity rejectCourseRegistrationByIds(
			@RequestBody Set<Integer> courseRegistrationIds, @RequestHeader(value="Authorization") String jwt) {
		try {
			adminService.updateCourseRegistrationStatusToByRegistrationIds(
					RegistrationStatus.REJECTED, 
					courseRegistrationIds);
			
			return adminService.SendApproveOrRejectNotification(jwt.substring(7));
		} catch (Exception e) {
			// We never reach here in case of Reject
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/course-registration/reject-all")
	public ResponseEntity rejectAllCourseRegistrations(@RequestHeader(value="Authorization") String jwt) {
		try {
			adminService
			.updateAllPendingCourseRegistrationsTo(
					RegistrationStatus.REJECTED);
			
			return adminService.SendApproveOrRejectNotification(jwt.substring(7));
		} catch (Exception e) {
			// We never reach here in case of Reject
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/course/add")
	public ResponseEntity addCourse(
			@RequestBody CourseRequestDTO courseRequestDTO) {
		return new ResponseEntity(adminService.add(courseRequestDTO), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/course/add-all")
	public ResponseEntity addAllCourses(
			@RequestBody Collection<CourseRequestDTO> courseRequestDTOs) {
		return new ResponseEntity(adminService.addAll(courseRequestDTOs), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.DELETE,
			value = "/course/drop/{courseId}")
	public ResponseEntity removeCourse(@PathVariable Integer courseId) {
		try {
			Boolean response = adminService.removeCourseById(courseId);
			return new ResponseEntity(response, HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException 
			| CannotDropCourseAssignedToProfessorException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
	}
	
	// TODO: Add /drop-all API
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.PUT,
			value = "/course/assign-professor")
	public ResponseEntity assignCourseToProfessor(
			@RequestBody CourseIdProfessorIdDTO professorIdCourseIdDTO) {
		try {
			adminService.assignCourseToProfessor(
					professorIdCourseIdDTO.getCourseId(),
					professorIdCourseIdDTO.getProfessorId());
			return new ResponseEntity(HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException
			| UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/course/courseIds/{registrationId}/")
	public ResponseEntity viewRegistrationCourseIdsByRegistrationId(
			@PathVariable(value = "registrationId") Integer registrationId) {
			List<Integer> registeredCourseIds = adminService
					.getRegisteredCourseIdsByRegistrationId(registrationId);
			return new ResponseEntity(registeredCourseIds,HttpStatus.OK);
		
		}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/account-registration/approve")
	public ResponseEntity approveUserAccountRegistrationByIds(
			@RequestBody Set<Integer> userRegistrationIds) {
		try {
			return new ResponseEntity(adminService.
					updateAccountRegistrationStatusToByUserIds(
							RegistrationStatus.APPROVED, 
							userRegistrationIds),HttpStatus.OK);
		}
		catch(UserNotFoundException e) {
			return new ResponseEntity(e.getMessage()
					, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/account-registration/approve-all")
	public ResponseEntity approveAllUserAccountRegistrations() {
		return new ResponseEntity(
				adminService
				.updateAllPendingAccountRegistrationsTo(
						RegistrationStatus.APPROVED)
				, HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.PUT,
			value = "/account-registration/reject")
	public ResponseEntity rejectUserAccountRegistrationsByIds(
			@RequestBody Collection<Integer> userRegistrationIds) {
		try {
			return new ResponseEntity(adminService.
					updateAccountRegistrationStatusToByUserIds(
							RegistrationStatus.REJECTED, 
							userRegistrationIds),HttpStatus.OK);
		}
		catch(UserNotFoundException e) {
			return new ResponseEntity(e.getMessage()
					, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/account-registration/reject-all")
	public ResponseEntity rejectAllUserAccountRegistrations() {
		return new ResponseEntity(
				adminService
				.updateAllPendingAccountRegistrationsTo(
						RegistrationStatus.REJECTED)
				, HttpStatus.OK);
	}
}
