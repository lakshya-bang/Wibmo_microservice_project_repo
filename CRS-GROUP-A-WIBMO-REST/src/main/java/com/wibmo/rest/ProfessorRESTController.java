/**
 * 
 */
package com.wibmo.rest;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Professor;
import com.wibmo.business.ProfessorService;
import com.wibmo.business.ProfessorServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping(value="/professor")
public class ProfessorRESTController {
	private static final Logger logger = LogManager.getLogger(ProfessorRESTController.class);
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.GET,
//		    value = "/hello")
//	@ResponseBody
//	public String hello() {
//		return "hello";
//	}
	
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/{id}")
	@ResponseBody
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
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/exists/{id}")
	@ResponseBody
	public ResponseEntity getProfessorifExists(@PathVariable("id") Integer professorId) {
		boolean professorExist = professorService.isProfessorExistsById(professorId);
		if (professorExist == false) {
			return new ResponseEntity("Professor with Id: " + professorId + " does not exist.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("Professor with Id: " + professorId + " exists.", HttpStatus.OK);
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
