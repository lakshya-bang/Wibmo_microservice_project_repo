/**
 * 
 */
package com.wibmo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dao.StudentDAO;
import com.wibmo.dao.StudentDAOImpl;
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
		private StudentServiceImpl studentOperation;

		
		
		// NOTE:
		// Need to replace all the GET / PUT /POST / DELETE mapping
		// by @RequestMapping annotation.
		
		@GetMapping("/{id}")
		public ResponseEntity getStudent(@PathVariable("id") Integer id) {

			Student student = studentOperation.getStudentById(id);
			if (student == null) {
				return new ResponseEntity("No student found for ID " + id, HttpStatus.NOT_FOUND);
			}

		return new ResponseEntity(student, HttpStatus.OK);
	}

	// TODO: Add Get Report Card API
	
}
