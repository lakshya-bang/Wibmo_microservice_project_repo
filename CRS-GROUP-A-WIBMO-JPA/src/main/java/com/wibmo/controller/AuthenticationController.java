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


public class AuthenticationController {
	
	@Autowired
	private AuthenticationServiceImpl authenticationService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/login")
	public ResponseEntity login(@RequestBody Creds creds) {
		User user = authenticationService.login(creds.userName, creds.password);
		if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(
				user.getRegistrationStatus())) {
			return new ResponseEntity("User Account Not Approved by Admin", HttpStatus.OK);
		}
		return new ResponseEntity(user,HttpStatus.OK);
	}
	
}

@Component
class Creds{
	String userName;
	String password;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
} 