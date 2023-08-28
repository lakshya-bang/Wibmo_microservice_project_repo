/**
 * 
 */
package com.wibmo.controller;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.controller.ProfessorController;
import com.wibmo.entity.Professor;


/**
 * 
 */
@RestController
@RequestMapping("/professor")
public class ProfessorController {
	
	private static final Logger logger = LogManager.getLogger(ProfessorController.class);
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/{id}")
	public ResponseEntity getProfessorByID(@PathVariable("id") Integer professorId) {

		Professor professor = professorService.getProfessorById(professorId);
		if (professor == null) {
			return new ResponseEntity("No Professor found for ID " + professorId, (HttpStatus.NOT_FOUND));
		}
		return new ResponseEntity(professor, HttpStatus.OK);
	}

}
