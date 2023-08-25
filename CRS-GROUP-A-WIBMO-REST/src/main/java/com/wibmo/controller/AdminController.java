/**
 * 
 */
package com.wibmo.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wibmo.service.UserServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserServiceImpl userService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/user/pending-approval")
	public ResponseEntity viewPendingApprovals() {
		try {
			return new ResponseEntity(userService.viewAccountsPendingForApproval(),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/user/reject/{userId}")
	public ResponseEntity rejectUser(@PathVariable("userId") Integer userId) {
		try {
			if(userService.rejectLoginById(userId)) {
				return new ResponseEntity("The user with user ID: " + userId+" has been rejected.", HttpStatus.OK);
			}
			else {
				return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.GET,
		    value = "/user/approve/{userId}")
	public ResponseEntity approveUser(@PathVariable("userId") Integer userId) {
		try {
			if(userService.approveLoginById(userId)) {
				return new ResponseEntity("The user with user ID: " + userId+" has been approved.", HttpStatus.OK);
			}
			else {
				return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
