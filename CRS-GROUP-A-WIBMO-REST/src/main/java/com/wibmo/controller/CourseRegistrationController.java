/**
 * 
 */
package com.wibmo.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Student;
import com.wibmo.dto.RegistrationDetails;
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
	
	// TODO: View methods need Projection Mapping
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
	public ResponseEntity register(@RequestBody RegistrationDetails registrationDetails ) {
		try {
			courseRegistrationService.register(registrationDetails.getPrimaryCourseIds(), registrationDetails.getAlternativeCourseIds(), registrationDetails.getStudent());
			return new ResponseEntity(registrationDetails,HttpStatus.OK);
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
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view-registered-students/{courseId}")
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
		    method = RequestMethod.POST,
		    value = "/add-course/{studentId}/{courseId}/{semester}")
	
	public ResponseEntity addCourse(@PathVariable Integer studentId, @PathVariable Integer semester, @PathVariable Integer courseId) {
		try {
			courseRegistrationService.addCourse(courseId, new Student(studentId,null,null,semester));
			return new ResponseEntity("Course Id added successfully: " + courseId,HttpStatus.OK);
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
		    value = "/drop-course/{studentId}/{courseId}/{semester}")
	public ResponseEntity dropCourse(@PathVariable Integer studentId, @PathVariable Integer semester, @PathVariable Integer courseId) {
		try {
			courseRegistrationService.dropCourse(courseId, new Student(studentId,null,null,semester));
			return new ResponseEntity("Course Id dropped successfully: " + courseId,HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException
			| StudentNotRegisteredForSemesterException
			| StudentNotRegisteredForCourseInSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view-registration-status/{studentId}/{semester}")
	
	public ResponseEntity viewRegistrationStatusByStudentId(@PathVariable Integer studentId, @PathVariable Integer semester) {
		RegistrationStatus regStatus;
		try {
			regStatus = courseRegistrationService.getRegistrationStatusByStudent(new Student(studentId,null,null,semester));
			return new ResponseEntity(regStatus.toString(),HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
