/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.entity.Student;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.StudentServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentServiceImpl studentService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get/{id}")
	public ResponseEntity getStudent(@PathVariable("id") Integer id) {
		Student student;
		try {
			student = studentService.getStudentById(id);
			return new ResponseEntity(student, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/get-all")
	public ResponseEntity getAllStudents() {
		return new ResponseEntity(studentService.getAllStudents(), HttpStatus.OK);
	}
	
}
