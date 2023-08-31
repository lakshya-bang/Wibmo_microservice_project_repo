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

import com.wibmo.dto.CourseIdProfessorIdDTO;
import com.wibmo.dto.CourseProfessorDTO;
import com.wibmo.entity.Course;
import com.wibmo.enums.CourseType;
import com.wibmo.service.CourseServiceImpl;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
@RestController
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private CourseServiceImpl courseService;
	
	// TODO: View Method needs Projection Mapping
	@RequestMapping(produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/get/{semester}")
	public ResponseEntity viewCourseDetailsBySemester(
			@PathVariable Integer semester) {
		try {
			List<CourseProfessorDTO> courses = courseService.getCourseDetailsBySemester(semester);
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/get/assigned/{professorId}")
	public ResponseEntity getCoursesAssignedToProfessor(
			@PathVariable Integer professorId){
		try {
			List<Course> courses = courseService.getCoursesAssignedToProfessor(professorId);
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/get/course-type/{courseId}")
	public ResponseEntity getCourseTypeByCourseId(
			@PathVariable Integer courseId) {
		try {
			CourseType courseType = courseService.getCourseTypeByCourseId(courseId);
			return new ResponseEntity(courseType,HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException e) {
			// TODO: Add stack trace to Logger
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	// TODO: View Method needs Projection Mapping
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/get-all")
	public ResponseEntity getAllCourses() {
		try {
			List<Course> courses = courseService.getAllCourses();
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/add")
	public ResponseEntity addCourse(@RequestBody Course course) {
		return new ResponseEntity(courseService.add(course), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.DELETE,
			value = "/drop/{courseId}")
	public ResponseEntity removeCourse(@PathVariable Integer courseId) {
		try {
			Boolean response = courseService.removeCourseById(courseId);
			return new ResponseEntity(response, HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException 
			| CannotDropCourseAssignedToProfessorException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/assign")
	public ResponseEntity assignCourseToProfessor(
			@RequestBody CourseIdProfessorIdDTO professorIdCourseIdDTO) {
		try {
			courseService.assignCourseToProfessor(
					professorIdCourseIdDTO.getCourseId(),
					professorIdCourseIdDTO.getProfessorId());
			return new ResponseEntity(HttpStatus.OK);
		}
		catch(CourseNotExistsInCatalogException
			| UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
