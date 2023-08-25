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
import com.wibmo.dto.CourseRegistrationDTO;
import com.wibmo.dto.StudentCourseIdDTO;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.service.CourseRegistrationServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/course-registration")
public class CourseRegistrationController {
	
	@Autowired
	private CourseRegistrationServiceImpl courseRegistrationService;
	
	/*
	 * TODO: View methods need Projection Mapping
	 */
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.GET,
//		    value = "/view-registration-details/{studentId}/{semester}")
//	public ResponseEntity viewRegistrationDetailsByStudentId(@PathVariable Integer studentId, @PathVariable Integer semester) {
//		CourseRegistration courseRegistration;
//		try {
//			courseRegistration = courseRegistrationService.findByStudent(new Student(studentId,null,null,semester));
//			return new ResponseEntity(courseRegistration),HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(
			@RequestBody CourseRegistrationDTO courseRegistrationDTO) {
		try {
			
			courseRegistrationService.register(
					courseRegistrationDTO.getPrimaryCourseIds(), 
					courseRegistrationDTO.getAlternativeCourseIds(), 
					courseRegistrationDTO.getStudent());
			
			return new ResponseEntity("Course Registration sent to Admin for Approval!", HttpStatus.OK);
		}
		catch(StudentAlreadyRegisteredForSemesterException 
			| CourseNotExistsInCatalogException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// TODO: View method needs Projection Mapping
//	@RequestMapping(
//			produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.GET,
//		    value = "/view-registered-courses/{studentId}/{semester}")	
//	public ResponseEntity viewRegisteredCoursesByStudentId(@PathVariable Integer studentId, @PathVariable Integer semester) {
//		List<RegisteredCourse> registeredCourses;
//		try {
//			registeredCourses = courseRegistrationService
//					.viewRegisteredCoursesByStudent(new Student(studentId,null,null,semester));
//			return new ResponseEntity(registeredCourses,HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view/students/{courseId}")
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
		    value = "/add-course")
	
	public ResponseEntity addCourse(
			@RequestBody StudentCourseIdDTO studentCourseIdDTO) {
		try {
			courseRegistrationService.addCourse(
					studentCourseIdDTO.getCourseId(), 
					studentCourseIdDTO.getStudent());
			return new ResponseEntity(
					"Course Id added successfully: " + studentCourseIdDTO.getCourseId(),
					HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException 
			| StudentAlreadyRegisteredForCourseInSemesterException
			| StudentAlreadyRegisteredForAllAlternativeCoursesException
			| StudentAlreadyRegisteredForAllPrimaryCoursesException
			| CourseNotExistsInCatalogException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/drop-course")
	public ResponseEntity dropCourse(
			@RequestBody StudentCourseIdDTO studentCourseIdDTO) {
		try {
			courseRegistrationService
				.dropCourse(
					studentCourseIdDTO.getCourseId(),
					studentCourseIdDTO.getStudent());
			return new ResponseEntity(
					"Course Id dropped successfully: " + studentCourseIdDTO.getCourseId(),
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
		    value = "/view/student/status")
	public ResponseEntity viewRegistrationStatusByStudentId(
			@RequestBody Student student) {
		RegistrationStatus regStatus;
		try {
			regStatus = courseRegistrationService.getRegistrationStatusByStudent(student);
			return new ResponseEntity(regStatus.toString(),HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/*
	 * To show all the pending courses registration requests.
	 */
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view/pending")
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
		return new ResponseEntity(
				courseRegistrationService
					.updateCourseRegistrationStatusToByRegistrationIds(
						RegistrationStatus.APPROVED, 
				courseRegistrationIds), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/approve-all")
	public ResponseEntity approveAllCourseRegistrations() {
		return new ResponseEntity(
				courseRegistrationService
					.updateAllPendingCourseRegistrationsTo(
						RegistrationStatus.APPROVED)
				, HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/reject")
	public ResponseEntity rejectCourseRegistrationByIds(
			@RequestBody Set<Integer> courseRegistrationIds) {
		return new ResponseEntity(
				courseRegistrationService
					.updateCourseRegistrationStatusToByRegistrationIds(
						RegistrationStatus.REJECTED, 
				courseRegistrationIds), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/reject-all")
	public ResponseEntity rejectAllCourseRegistrations() {
		return new ResponseEntity(
				courseRegistrationService
					.updateAllPendingCourseRegistrationsTo(
						RegistrationStatus.REJECTED),
				HttpStatus.OK);
	}
	
}
