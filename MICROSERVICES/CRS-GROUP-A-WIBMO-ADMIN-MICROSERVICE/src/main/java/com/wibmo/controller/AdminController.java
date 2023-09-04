/**
 * 
 */
package com.wibmo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import com.wibmo.service.AdminServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@PreAuthorize("hasAuthority('Role.ADMIN')")
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
		    value = "/get/{studentId}/{semester}")
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
		    value = "/get/status/{studentId}/{semester}")
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
		    value = "/get/pending")
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
		    value = "/approve")
	public ResponseEntity approveCourseRegistrationByIds(
			@RequestBody Set<Integer> courseRegistrationIds) {
		try {
			return new ResponseEntity(
					adminService
						.updateCourseRegistrationStatusToByRegistrationIds(
							RegistrationStatus.APPROVED, 
					courseRegistrationIds), HttpStatus.OK);
		} catch (CannotApproveCourseRegistrationPaymentPendingException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/approve-all")
	public ResponseEntity approveAllCourseRegistrations() {
		try {
			return new ResponseEntity(
					adminService
						.updateAllPendingCourseRegistrationsTo(
							RegistrationStatus.APPROVED)
					, HttpStatus.OK);
		} catch (CannotApproveCourseRegistrationPaymentPendingException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/reject")
	public ResponseEntity rejectCourseRegistrationByIds(
			@RequestBody Set<Integer> courseRegistrationIds) {
		try {
			return new ResponseEntity(
					adminService
						.updateCourseRegistrationStatusToByRegistrationIds(
							RegistrationStatus.REJECTED, 
					courseRegistrationIds), HttpStatus.OK);
		} catch (Exception e) {
			// We never reach here in case of Reject
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/reject-all")
	public ResponseEntity rejectAllCourseRegistrations() {
		try {
			return new ResponseEntity(
					adminService
						.updateAllPendingCourseRegistrationsTo(
							RegistrationStatus.REJECTED),
					HttpStatus.OK);
		} catch (Exception e) {
			// We never reach here in case of Reject
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/add")
	public ResponseEntity addCourse(
			@RequestBody CourseRequestDTO courseRequestDTO) {
		return new ResponseEntity(adminService.add(courseRequestDTO), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/add-all")
	public ResponseEntity addAllCourses(
			@RequestBody Collection<CourseRequestDTO> courseRequestDTOs) {
		return new ResponseEntity(adminService.addAll(courseRequestDTOs), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.DELETE,
			value = "/drop/{courseId}")
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
			value = "/assign")
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
		    value = "/get/courseIds/{registrationId}/")
	public ResponseEntity viewRegistrationCourseIdsByRegistrationId(
			@PathVariable(value = "registrationId") Integer registrationId) {
			List<Integer> registeredCourseIds = adminService
					.getRegisteredCourseIdsByRegistrationId(registrationId);
			return new ResponseEntity(registeredCourseIds,HttpStatus.OK);
		
		}
}
