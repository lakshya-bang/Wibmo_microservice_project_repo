/**
 * 
 */
package com.wibmo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Student;
import com.wibmo.dto.RegisteredCourse;
import com.wibmo.dto.RegistrationDetails;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.service.CourseRegistrationOperationImpl;

/**
 * 
 */
@RestController
@Component
@RequestMapping("/course-registration")
public class CourseRegistrationController {
	@Autowired
	private CourseRegistrationOperationImpl courseRegistrationOperation;
	
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.GET,
//		    value = "/view-registration-details/{studentId}/{semester}")
//	public ResponseEntity viewRegistrationDetailsByStudentId(@PathVariable Integer studentId, @PathVariable Integer semester) {
//		CourseRegistration courseRegistration;
//		try {
//			courseRegistration = courseRegistrationOperation.findByStudent(new Student(studentId,null,null,semester));
//			return new ResponseEntity(courseRegistration),HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(@RequestBody RegistrationDetails registrationDetails ) {
		try {
			courseRegistrationOperation.register(registrationDetails.getPrimaryCourseIds(), registrationDetails.getAlternativeCourseIds(), registrationDetails.getStudent());
			return new ResponseEntity(registrationDetails,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view-registered-courses/{studentId}/{semester}")
	
	public ResponseEntity viewRegisteredCoursesByStudentId(@PathVariable Integer studentId, @PathVariable Integer semester) {
		List<RegisteredCourse> registeredCourses;
		try {
			registeredCourses = courseRegistrationOperation.viewRegisteredCoursesByStudent(new Student(studentId,null,null,semester));
			return new ResponseEntity(registeredCourses,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view-registered-students/{courseId}")
	public ResponseEntity viewRegisteredStudentsByCourseId(@PathVariable Integer courseId) {
		List<Student> students;
		try {
			students = courseRegistrationOperation.getRegisteredStudentsByCourseId(courseId);
			return new ResponseEntity(students,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/add-course/{studentId}/{courseId}/{semester}")
	
	public ResponseEntity addCourse(@PathVariable Integer studentId, @PathVariable Integer semester, @PathVariable Integer courseId) {
		try {
			courseRegistrationOperation.addCourse(courseId, new Student(studentId,null,null,semester));
			return new ResponseEntity("Course Id added successfully: " + courseId,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/drop-course/{studentId}/{courseId}/{semester}")
	
	public ResponseEntity dropCourse(@PathVariable Integer studentId, @PathVariable Integer semester, @PathVariable Integer courseId) {
		try {
			courseRegistrationOperation.dropCourse(courseId, new Student(studentId,null,null,semester));
			return new ResponseEntity("Course Id dropped successfully: " + courseId,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/view-registration-status/{studentId}/{semester}")
	
	public ResponseEntity viewRegistrationStatusByStudentId(@PathVariable Integer studentId, @PathVariable Integer semester) {
		RegistrationStatus regStatus;
		try {
			regStatus = courseRegistrationOperation.getRegistrationStatusByStudent(new Student(studentId,null,null,semester));
			return new ResponseEntity(regStatus.toString(),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
