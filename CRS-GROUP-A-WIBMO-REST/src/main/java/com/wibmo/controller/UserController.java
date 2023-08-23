/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dto.RegistrationDetails;
import com.wibmo.dto.UserRegistrationDetails;
import com.wibmo.service.AdminOperationImpl;
import com.wibmo.service.ProfessorOperationImpl;
import com.wibmo.service.StudentOperationImpl;
import com.wibmo.service.UserOperationImpl;
import com.wibmo.utils.UserControllerUtils;

/**
 * 
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserOperationImpl userOperation;
	@Autowired
	private StudentOperationImpl studentOperation;
	@Autowired
	private ProfessorOperationImpl professorOperation;
	@Autowired
	private AdminOperationImpl adminOperation;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(@RequestBody UserRegistrationDetails userRegistrationDetails ) {
		try {
			User user = UserControllerUtils.saveRegDetailsToUser(userRegistrationDetails);
			userOperation.add(user);
			user.setUserId(userOperation.getUserIdByEmail(user.getUserEmail()));
			switch(user.getUserType()) {
			case ADMIN:{
				Admin admin = new Admin(user.getUserId(),user.getUserEmail(),userRegistrationDetails.getName());
				adminOperation.add(admin);
				return new ResponseEntity(admin,HttpStatus.OK);
			}

			case PROFESSOR:{
				Professor professor = new Professor(user.getUserId(),user.getUserEmail(),userRegistrationDetails.getName(),userRegistrationDetails.getDepartment());
				professorOperation.add(professor);
				return new ResponseEntity(professor,HttpStatus.OK);
			}
				
			case STUDENT:{
				Student student = new Student(user.getUserId(),user.getUserEmail(),userRegistrationDetails.getName(),userRegistrationDetails.getSemester());
				studentOperation.add(student);

				return new ResponseEntity(student,HttpStatus.OK);
				}
			default:{
				return new ResponseEntity("Invalid User Type.", HttpStatus.BAD_REQUEST);
			}
			}
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
