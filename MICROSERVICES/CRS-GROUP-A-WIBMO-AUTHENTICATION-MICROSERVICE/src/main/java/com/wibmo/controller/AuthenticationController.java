package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.dto.UserRegistrationDetailsDTO;
import com.wibmo.dto.User_Creds;
import com.wibmo.exception.UserNotApprovedException;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.service.JwtUserDetailsService;
import com.wibmo.utils.JwtTokenUtil;
import com.wibmo.utils.LoginUtil;

/**
 * 
 */
@RestController
@Component
@RequestMapping(value = "/api/authentication")
@CrossOrigin
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private LoginUtil loginUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/login")
	public ResponseEntity login(@RequestBody User_Creds creds) {
		
		try {
			authenticate(creds.getUserEmail(), creds.getPassword());
		} catch (DisabledException|BadCredentialsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity("Incorrect Creditials", HttpStatus.UNAUTHORIZED);
		} catch (UserNotApprovedException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(creds.getUserEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(token);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON, 
		    method = RequestMethod.POST,
		    value = "/register")
	public ResponseEntity register(@RequestBody UserRegistrationDTO userDTO) {
		try {
			userDetailsService.save(userDTO);
		} catch (UserWithEmailAlreadyExistsException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUserEmail());
//
//		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok("Account Sent to Admin for approval.");
	}
	
	private void authenticate(String username, String password) throws DisabledException,BadCredentialsException,UserNotApprovedException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			loginUtil.isApproved(username);
		} catch (DisabledException e) {
			throw e;
		} catch (BadCredentialsException e) {
			throw e;
		} catch(UserNotApprovedException e) {
			throw e;
		}
	}
}
