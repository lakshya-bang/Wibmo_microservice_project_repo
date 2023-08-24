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

import com.wibmo.bean.Course;
import com.wibmo.dto.ProfessorIdCourseIdDTO;
import com.wibmo.enums.CourseType;
import com.wibmo.service.CourseServiceImpl;
import com.wibmo.exception.CannotDropCourseAssignedToProfessorException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
@RestController
public class CourseController {
	
	@Autowired
	private CourseServiceImpl courseService;
	
	// TODO: View Method needs Projection Mapping
//	@RequestMapping(produces = MediaType.APPLICATION_JSON,
//			method = RequestMethod.GET,
//			value = "/view-Course-Details-By-Semester/{currentSemester}")
//	public ResponseEntity viewCourseDetailsBySemester(
//			@PathVariable Integer currentSemester) {
//		try {
//			List<Course> courses = courseService.viewCourseDetailsBySemester(currentSemester);
//			return new ResponseEntity(courses,HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error",HttpStatus.NOT_FOUND);
//		}
//		
//	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/get-Courses-Assigned-To-Professor/{professorId}")
	public ResponseEntity getCoursesAssignedToProfessor(
			@PathVariable Integer professorId){
		try {
			List<Course> courses=courseService.getCoursesAssignedToProfessor(professorId);
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/getCourseTypeByCourseId/{courseId}")
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
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/isCourseExistsInCatalog/{courseId}")
	public ResponseEntity isCourseExistsInCatalog(
			@PathVariable Integer courseId) {
		Boolean courseExists = courseService.isCourseExistsInCatalog(courseId);
		return new ResponseEntity(courseExists,HttpStatus.OK);
	}
	
	// TODO: View Method needs Projection Mapping
//	@RequestMapping(
//			produces=MediaType.APPLICATION_JSON,
//			method=RequestMethod.GET,
//			value="/viewAllCourses")
//	public ResponseEntity viewAllCourses() {
//		List<Course> courses;
//		try {
//			courses = courseService.viewAllCourses();
//			return new ResponseEntity(courses,HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
//		}
//	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/add-Course")
	public ResponseEntity addCourse(
			@RequestBody Course course) {
//		try {
		System.out.println("Course is :"+course);
			Boolean response = courseService.add(course);
			return new ResponseEntity(response, HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
//		}
		
	}
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.DELETE,
			value = "/drop-Course/{courseId}")
	public ResponseEntity removeCourse(
			@PathVariable Integer courseId) {
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
			value = "/assign-Course-To-Professor")
	public ResponseEntity assignCourseToProfessor(
			@RequestBody ProfessorIdCourseIdDTO professorIdCourseIdDTO) {
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
	
	@RequestMapping(
			produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/is-Professor-Assigned-For-Course")
	public ResponseEntity isProfessorAssignedForCourse(
			@RequestBody ProfessorIdCourseIdDTO professorIdCourseIdDTO)
			 {
		try {
			Boolean response = courseService.isProfessorAssignedForCourse(professorIdCourseIdDTO.getProfessorId(),professorIdCourseIdDTO.getCourseId());
			return new ResponseEntity(response,HttpStatus.OK);
		}
		catch(UserNotFoundException 
			| CourseNotExistsInCatalogException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
