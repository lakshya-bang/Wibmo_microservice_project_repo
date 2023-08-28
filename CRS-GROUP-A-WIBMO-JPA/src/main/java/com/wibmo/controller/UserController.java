/**
 * 
 */
package com.wibmo.controller;

import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.DepartmentCannotBeEmptyException;
import com.wibmo.exception.SemesterCannotBeEmptyException;
import com.wibmo.service.UserServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/pending")
	public ResponseEntity viewPendingApprovals() {
		try {
			return new ResponseEntity(
					userService.getAccountsPendingForApproval(),
					HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(
			@RequestBody UserRegistrationDTO userRegistrationDTO) {
		try {
			userService.add(userRegistrationDTO);
			return new ResponseEntity(
					"User Account Registration sent to Admin for Approval.",
					HttpStatus.OK);
		} catch(UserWithEmailAlreadyExistsException 
				| SemesterCannotBeEmptyException 
				| DepartmentCannotBeEmptyException e) {
			return new ResponseEntity(
					e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.PUT,
		    value = "/approve")
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
		    value = "/approve-all")
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
		    value = "/reject")
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
		    value = "/reject-all")
	public ResponseEntity rejectAllUserAccountRegistrations() {
		return new ResponseEntity(
				userService
				.updateAllPendingAccountRegistrationsTo(
						RegistrationStatus.REJECTED),
				HttpStatus.OK);
	}
	
}
