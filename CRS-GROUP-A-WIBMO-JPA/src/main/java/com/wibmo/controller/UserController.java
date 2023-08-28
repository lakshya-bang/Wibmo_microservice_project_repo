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

import com.wibmo.dto.CourseRegistrationDTO;
import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.service.AdminServiceImpl;
import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.service.StudentServiceImpl;
import com.wibmo.service.UserServiceImpl;
import com.wibmo.utils.UserControllerUtils;

/**
 * 
 */

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
			@RequestBody UserRegistrationDTO userRegistrationDTO) {
		try {
			User user = UserControllerUtils.saveRegDetailsToUser(userRegistrationDTO);
			userService.add(user);
			switch(user.getUserType()) {
			case ADMIN:
				adminService.add(new Admin(userService.getUserIdByEmail(user.getUserEmail()), user.getUserEmail(), userRegistrationDTO.getName()));
				break;
			case PROFESSOR:
				professorService.add(new Professor(userService.getUserIdByEmail(user.getUserEmail()), user.getUserEmail(), userRegistrationDTO.getName(), userRegistrationDTO.getDepartment()));
				break;
			case STUDENT:
				studentService.add(new Student(userService.getUserIdByEmail(user.getUserEmail()), user.getUserEmail(), userRegistrationDTO.getName(), userRegistrationDTO.getSemester()));
				break;
			}
			return new ResponseEntity(
					"User Account Registration sent to Admin for Approval.",
					HttpStatus.OK);
		} catch(UserWithEmailAlreadyExistsException e) {
			return new ResponseEntity(
					e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// TODO: Add Approve / Reject APIs
	
}
