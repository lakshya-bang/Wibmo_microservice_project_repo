package com.wibmo.controller;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.http.auth.InvalidCredentialsException;
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
import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.exception.CourseNotAvailableDueToSeatsFullException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
//import com.wibmo.exception.IncorrectPasswordException;
import com.wibmo.exception.InvalidCourseForCourseTypeException;
//import com.wibmo.exception.NoAccountExistsWithEmailException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllCoursesOfTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotEligibleForCourseRegistrationException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.StudentServiceImpl;

@RestController
@RequestMapping(value = "/api/student")
@CrossOrigin
@PreAuthorize("hasAuthority('Role.STUDENT')")
public class StudentController {
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get/{id}")
	public ResponseEntity getStudent(@PathVariable("id") Integer id) {
		return new ResponseEntity(studentService.getStudentById(id), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get")
	public ResponseEntity getStudent(
			@RequestBody Collection<Integer> ids) {
		return new ResponseEntity(studentService.getAllStudentsByIds(ids), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get-all")
	public ResponseEntity getAllStudents() {
		return new ResponseEntity(studentService.getAllStudents(), HttpStatus.OK);
	}
	
	/************************************ Course Routes ********************************/
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/course/{id}")
	public ResponseEntity getCourseById(@PathVariable("id") Integer courseId) {
		try {
			return new ResponseEntity(
					studentService.getCourseDetailsById(courseId),
					HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/course/get-all")
	public ResponseEntity getAllCourses() {
		try {
			return new ResponseEntity(studentService.getAllCourses(),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/course/{semester}")
	public ResponseEntity viewCourseDetailsBySemester(
			@PathVariable Integer semester) {
		try {
			return new ResponseEntity(
					studentService.getCourseDetailsBySemester(semester),
					HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/course/type/{courseId}")
	public ResponseEntity getCourseTypeByCourseId(
			@PathVariable Integer courseId) {
		try {
			return new ResponseEntity(
					studentService.getCourseTypeByCourseId(courseId),
					HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException e) {
			// TODO: Add stack trace to Logger
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	/***************************** Course Registration Routes **************************/
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/course-registration/{studentId}/{semester}")
	public ResponseEntity viewRegistrationDetailsByStudentId(
			@PathVariable(value = "studentId") Integer studentId, 
			@PathVariable(value = "semester") Integer semester) {
		try {
			return new ResponseEntity(
					studentService.getRegisteredCoursesByStudentIdAndSemester(studentId, semester),
					HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/course-registration/register")
	public ResponseEntity register(
			@RequestBody CourseRegistrationRequestDTO courseRegistrationDTO) {
		try {
			
			studentService.register(courseRegistrationDTO);
			
			return new ResponseEntity(
					"Course Registration sent to Admin for Approval!", 
					HttpStatus.OK);
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
		    value = "/course-registration/students/{courseId}")
	public ResponseEntity viewRegisteredStudentsByCourseId(@PathVariable Integer courseId) {
		try {
			return new ResponseEntity(
					studentService.getRegisteredStudentsByCourseId(courseId),
					HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/course-registration/add")
	public ResponseEntity addCourse(
			@RequestBody AddDropCourseDTO addCourseDTO) {
		try {
			studentService.addCourse(
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
		    value = "/course-registration/drop")
	public ResponseEntity dropCourse(
			@RequestBody AddDropCourseDTO dropCourseDTO) {
		try {
			studentService
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
		    value = "/course-registration/status/{studentId}/{semester}")
	public ResponseEntity viewRegistrationStatusByStudentId(
			@PathVariable(value = "studentId") Integer studentId,
			@PathVariable(value = "semester") Integer semester) {
		try {
			return new ResponseEntity(
					studentService
						.getRegistrationStatusByStudentIdAndSemester(studentId, semester)
						.toString(),
					HttpStatus.OK);
		}
		catch(StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/********************************** Report Card Routes *****************************/
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/report-card/{studentId}/{semester}")
	public ResponseEntity getReportCardForSemester(
			@PathVariable(value = "studentId") Integer studentId,
			@PathVariable(value = "semester") Integer semester){
		try {
			return new ResponseEntity(
					studentService.getReportCardByStudentIdAndSemester(studentId, semester), 
					HttpStatus.OK);
		}
		catch(UserNotFoundException 
			| StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/report-card/{studentId}")
	public ResponseEntity getAllReportCards(
			@PathVariable(value = "studentId") Integer studentId){
		try {
			return new ResponseEntity(
					studentService.getSemesterToReportCardMapByStudentId(studentId), 
					HttpStatus.OK);
		}
		catch(UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
}
