/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.entity.Student;
import com.wibmo.service.StudentServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/student")
public class StudentController {

	// NOTE:
	// By default, this object is singleton
	// and inject the dependency without
	// using new keyword.
	@Autowired
	private StudentServiceImpl studentService;
	
	// NOTE:
	// Need to replace all the GET / PUT /POST / DELETE mapping
	// by @RequestMapping annotation.
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/get/{id}")
	public ResponseEntity getStudent(@PathVariable("id") Integer id) {
		Student student = studentService.getStudentById(id);
		if (student == null) {
			return new ResponseEntity("No student found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(student, HttpStatus.OK);
	}
}
