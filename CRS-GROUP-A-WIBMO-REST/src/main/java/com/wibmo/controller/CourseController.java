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
import com.wibmo.bean.Professor;
import com.wibmo.dto.ProfessorIdCourseIdDTO;
import com.wibmo.enums.CourseType;
import com.wibmo.service.CourseOperationImpl;

/**
 * 
 */
@RestController
public class CourseController {
	
	@Autowired
	private CourseOperationImpl courseOperation;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/view-Course-Details-By-Semester/{currentSemester}")
	public ResponseEntity viewCourseDetailsBySemester(
			@PathVariable Integer currentSemester) {
		try {
			List<Course> courses = courseOperation.viewCourseDetailsBySemester(currentSemester);
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/get-Courses-Assigned-To-Professor/{professorId}")
	public ResponseEntity getCoursesAssignedToProfessor(
			@PathVariable Integer professorId){
		try {
			List<Course> courses=courseOperation.getCoursesAssignedToProfessor(professorId);
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/getCourseTypeByCourseId/{courseId}")
	public ResponseEntity getCourseTypeByCourseId(
			@PathVariable Integer courseId) {
		try {
			CourseType courseType = courseOperation.getCourseTypeByCourseId(courseId);
			return new ResponseEntity(courseType,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/isCourseExistsInCatalog/{courseId}")
	public ResponseEntity isCourseExistsInCatalog(
			@PathVariable Integer courseId) {
		try {
			Boolean courseExists = courseOperation.isCourseExistsInCatalog(courseId);
			return new ResponseEntity(courseExists,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/viewAllCourses")
	public ResponseEntity viewAllCourses() {
		List<Course> courses;
		try {
			courses = courseOperation.viewAllCourses();
			return new ResponseEntity(courses,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/add-Course")
	public ResponseEntity addCourse(
			@RequestBody Course course) {
//		try {
		System.out.println("Course is :"+course);
			Boolean response = courseOperation.addCourse(course);
			return new ResponseEntity(response,HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
//		}
		
	}
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/drop-Course/{courseId}")
	public ResponseEntity removeCourse(
			@PathVariable Integer courseId) {
		try {
			Boolean response = courseOperation.removeCourseById(courseId);
			return new ResponseEntity(response,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
		
	}
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/assign-Course-To-Professor")
	public ResponseEntity assignCourseToProfessor(
			@RequestBody ProfessorIdCourseIdDTO professorIdCourseIdDTO
			) {
		try {
			Boolean response = courseOperation.assignCourseToProfessor(professorIdCourseIdDTO.getCourseId(),professorIdCourseIdDTO.getProfessorId());
			return new ResponseEntity(response,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/view-Courses-Taught-By-Professor/{professorId}")
	public ResponseEntity viewCoursesTaughtByProfessor(
			@PathVariable Integer professorId) {
		try {
			List<Course> response = courseOperation.viewCoursesTaughtByProfessorId(professorId);
			return new ResponseEntity(response,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/is-Professor-Assigned-For-Course")
	public ResponseEntity isProfessorAssignedForCourse(
			@RequestBody ProfessorIdCourseIdDTO professorIdCourseIdDTO)
			 {
		try {
			Boolean response = courseOperation.isProfessorAssignedForCourse(professorIdCourseIdDTO.getProfessorId(),professorIdCourseIdDTO.getCourseId());
			return new ResponseEntity(response,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error !!",HttpStatus.NOT_FOUND);
		}

		
	}
	
}
