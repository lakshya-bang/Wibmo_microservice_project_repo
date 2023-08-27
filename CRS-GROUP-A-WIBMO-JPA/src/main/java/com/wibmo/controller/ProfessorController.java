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
@RequestMapping(value="/professor")
public class ProfessorController {
	
	private static final Logger logger = LogManager.getLogger(ProfessorController.class);
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.GET,
//		    value = "/hello")
//	@ResponseBody
//	public String hello() {
//		return "hello";
//	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/{id}")
	public ResponseEntity getProfessorByID(@PathVariable("id") Integer professorId) {

		Professor professor = professorService.getProfessorById(professorId);
		if (professor == null) {
			return new ResponseEntity("No Professor found for ID " + professorId, (HttpStatus.NOT_FOUND));
		}
		return new ResponseEntity(professor, HttpStatus.OK);
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/mapping")
	@ResponseBody
	public ResponseEntity getProfessorMappingbyProfessorId(@RequestBody Set<Integer> professorIds) {

		Map<Integer, Professor> professorMap = professorService.getProfessorIdToProfessorMap(professorIds);
		if (professorMap == null) {
			return new ResponseEntity("No Professor mapping found for ID " + professorIds, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(professorMap, HttpStatus.OK);
	}
	
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.POST,
//		    value = "/add")
//	@ResponseBody
//	public ResponseEntity createProfessor(@RequestBody Professor professor) {
//		professorService.add(professor);
//		return new ResponseEntity(professor, HttpStatus.OK);
//	}
	
}
