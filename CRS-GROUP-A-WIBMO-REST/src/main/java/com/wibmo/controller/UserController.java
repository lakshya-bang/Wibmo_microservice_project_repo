/**
 * 
 */
package com.wibmo.controller;

import java.util.Set;

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
<<<<<<< HEAD
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
=======
import com.wibmo.enums.RegistrationStatus;
>>>>>>> 03c728ac4bc7cc2c1e7d13523d748320846620f5
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
	
<<<<<<< HEAD
	// TODO: Add Approve / Reject APIs
=======
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/approve/")
	public ResponseEntity approveUserAccountRegistrationByIds(
			@RequestBody Set<Integer> userRegistrationIds) {
		return new ResponseEntity(
				userService.
				updateAccountRegistrationStatusToByUserIds(
						RegistrationStatus.APPROVED, 
						userRegistrationIds), HttpStatus.OK);
//		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/approve-all/")
	public ResponseEntity approveAllUserAccountRegistrations() {
		return new ResponseEntity(
				userService
				.updateAllPendingAccountRegistrationsTo(
						RegistrationStatus.APPROVED)
				, HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/reject/")
	public ResponseEntity rejectUserAccountRegistrationByIds(
			@RequestBody Set<Integer> userRegistrationIds) {
		return new ResponseEntity(
				userService
				.updateAccountRegistrationStatusToByUserIds(
						RegistrationStatus.REJECTED, 
						userRegistrationIds), HttpStatus.OK);
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/reject-all/")
	public ResponseEntity rejectAllUserAccountRegistrations() {
		return new ResponseEntity(
				userService
				.updateAllPendingAccountRegistrationsTo(
						RegistrationStatus.REJECTED),
				HttpStatus.OK);
	}
>>>>>>> 03c728ac4bc7cc2c1e7d13523d748320846620f5
	
}
