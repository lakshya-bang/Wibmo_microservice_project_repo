package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.service.ProfessorServiceImpl;

@RestController
@RequestMapping(value = "/api/professor/course-registration")
@CrossOrigin
@PreAuthorize("hasAuthority('Role.PROFESSOR')")
public class CourseRegistrationController {
	
	@Autowired
	ProfessorServiceImpl professorService;

	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/registered-students/{professorId}")
	public ResponseEntity CourseIdToRegisteredStudentsMappingByProfessorId( 
			@PathVariable Integer professorId) {
		try {
			 
			return new ResponseEntity(professorService
					.getCourseIdToRegisteredStudentsMappingByProfessorId(professorId),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
