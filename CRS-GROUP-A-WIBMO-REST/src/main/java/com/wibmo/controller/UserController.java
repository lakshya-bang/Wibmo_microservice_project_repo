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
import com.wibmo.service.AdminServiceImpl;
import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.service.StudentServiceImpl;
import com.wibmo.service.UserServiceImpl;
import com.wibmo.utils.UserControllerUtils;

/**
 * 
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	private AdminServiceImpl adminService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(
			@RequestBody UserRegistrationDetails userRegistrationDetails ) {
		try {
			User user = UserControllerUtils.saveRegDetailsToUser(userRegistrationDetails);
			userService.add(user);
			user.setUserId(userService.getUserIdByEmail(user.getUserEmail()));
			switch(user.getUserType()) {
			
			case ADMIN:{
				Admin admin = new Admin(user.getUserId(),user.getUserEmail(),userRegistrationDetails.getName());
				adminService.add(admin);
				return new ResponseEntity(admin,HttpStatus.OK);
			}

			case PROFESSOR:{
				Professor professor = new Professor(user.getUserId(),user.getUserEmail(),userRegistrationDetails.getName(),userRegistrationDetails.getDepartment());
				professorService.add(professor);
				return new ResponseEntity(professor,HttpStatus.OK);
			}
				
			case STUDENT:{
				Student student = new Student(user.getUserId(),user.getUserEmail(),userRegistrationDetails.getName(),userRegistrationDetails.getSemester());
				studentService.add(student);

				return new ResponseEntity(student,HttpStatus.OK);
			}
			
			default:{
				return new ResponseEntity("Invalid User Type.", HttpStatus.BAD_REQUEST);
			}
			} 
		} catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
