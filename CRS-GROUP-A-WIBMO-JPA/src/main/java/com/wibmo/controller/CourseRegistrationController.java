/**
 * 
 */
package com.wibmo.controller;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.entity.Student;
import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.dto.AddDropCourseDTO;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CannotApproveCourseRegistrationPaymentPendingException;
import com.wibmo.exception.CourseNotAvailableDueToSeatsFullException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.InvalidCourseForCourseTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllCoursesOfTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotEligibleForCourseRegistrationException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.CourseRegistrationServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/course-registration")
public class CourseRegistrationController {
	
	@Autowired
	private CourseRegistrationServiceImpl courseRegistrationService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/{studentId}/{semester}")
	public ResponseEntity viewRegistrationDetailsByStudentId(
			@PathVariable(value = "studentId") Integer studentId, 
			@PathVariable(value = "semester") Integer semester) {
		try {
			List<CourseResponseDTO> courseRegistrations = courseRegistrationService
					.getRegisteredCoursesByStudentIdAndSemester(studentId, semester);
			return new ResponseEntity(courseRegistrations,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(
			@RequestBody CourseRegistrationRequestDTO courseRegistrationDTO) {
		try {
			
			courseRegistrationService.register(courseRegistrationDTO);
			
			return new ResponseEntity("Course Registration sent to Admin for Approval!", HttpStatus.OK);
		}
		catch(StudentAlreadyRegisteredForSemesterException 
			| CourseNotExistsInCatalogException 
			| UserNotFoundException 
			| StudentNotEligibleForCourseRegistrationException 
			| InvalidCourseForCourseTypeException 
			| CourseNotAvailableDueToSeatsFullException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/students/{courseId}")
	public ResponseEntity viewRegisteredStudentsByCourseId(@PathVariable Integer courseId) {
		List<Student> students;
		try {
			students = courseRegistrationService.getRegisteredStudentsByCourseId(courseId);
			return new ResponseEntity(students,HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/add")
	public ResponseEntity addCourse(
			@RequestBody AddDropCourseDTO addCourseDTO) {
		try {
			courseRegistrationService.addCourse(
					addCourseDTO.getCourseId(),
					addCourseDTO.getStudentId(),
					addCourseDTO.getSemester());
			return new ResponseEntity(
					"Course Id added successfully: " + addCourseDTO.getCourseId(),
					HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException 
			| StudentAlreadyRegisteredForCourseInSemesterException
			| CourseNotExistsInCatalogException 
			| StudentAlreadyRegisteredForAllCoursesOfTypeException 
			| UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/drop")
	public ResponseEntity dropCourse(
			@RequestBody AddDropCourseDTO dropCourseDTO) {
		try {
			courseRegistrationService
				.dropCourse(
					dropCourseDTO.getCourseId(),
					dropCourseDTO.getStudentId(),
					dropCourseDTO.getSemester());
			return new ResponseEntity(
					"Course Id dropped successfully: " + dropCourseDTO.getCourseId(),
					HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException
			| StudentNotRegisteredForSemesterException
			| StudentNotRegisteredForCourseInSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/status/{studentId}/{semester}")
	public ResponseEntity viewRegistrationStatusByStudentId(
			@PathVariable(value = "studentId") Integer studentId,
			@PathVariable(value = "semester") Integer semester) {
		try {
			RegistrationStatus registrationStatus = courseRegistrationService
					.getRegistrationStatusByStudentIdAndSemester(studentId, semester);
			return new ResponseEntity(registrationStatus.toString(),HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/CourseIdToStudents/{professorId}")
	public ResponseEntity CourseIdToRegisteredStudentsMappingByProfessorId( 
			@PathVariable Integer professorId) {
		try {
			 
			return new ResponseEntity(courseRegistrationService
					.getCourseIdToRegisteredStudentsMappingByProfessorId(professorId),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/*
	 * To show all the pending courses registration requests.
	 */
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/pending")
	public ResponseEntity viewPendingCourseRegistrations() {
		return new ResponseEntity(
				courseRegistrationService
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
					courseRegistrationService
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
					courseRegistrationService
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
					courseRegistrationService
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
					courseRegistrationService
						.updateAllPendingCourseRegistrationsTo(
							RegistrationStatus.REJECTED),
					HttpStatus.OK);
		} catch (Exception e) {
			// We never reach here in case of Reject
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	
}
