/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.service.AuthenticationService;
import com.wibmo.service.AuthenticationServiceImpl;

/**
 * 
 */
//@RestController
//public class AuthenticationController {
//	
//	@Autowired
//	private AuthenticationServiceImpl authenticationService;
//	
//	@RequestMapping(
//			produces = MediaType.APPLICATION_JSON, 
//		    method = RequestMethod.POST,
//		    value = "/login")
//	public ResponseEntity login(@RequestBody Creds creds) {
//		User user = authenticationService.login(creds.userName, creds.password);
//		if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(
//				user.getRegistrationStatus())) {
//			return new ResponseEntity("User Account Not Approved by Admin", HttpStatus.OK);
//		}
//		return new ResponseEntity(user,HttpStatus.OK);
//	}
//	
//}
