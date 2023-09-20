package com.wibmo.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.exception.*;
import com.wibmo.service.ProfessorServiceImpl;


@RestController
@RequestMapping(value = "/api/professor/report-card")
@CrossOrigin
@PreAuthorize("hasAuthority('Role.PROFESSOR')")
public class ReportCardController {
	
	@Autowired
	ProfessorServiceImpl professorService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/upload-grades")
	public ResponseEntity upload(
			@RequestBody List<ReportCardRequestDTO> reportCardRequestDTOs){
		try {
			
			professorService.addAll(reportCardRequestDTOs);
			
			return new ResponseEntity("Grades added successfully!", HttpStatus.OK);
			
		} catch (StudentNotRegisteredForCourseException
				| CannotAddGradeStudentRegistrationNotApprovedException 
				| UserNotFoundException 
				| CourseNotExistsInCatalogException 
				| StudentIdCannotBeEmptyException 
				| CourseIdCannotBeEmptyException 
				| GradeCannotBeEmptyException 
				| GradeValueInvalidException 
				| ProfessorNotAssignedForCourseException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
		
	}
}
