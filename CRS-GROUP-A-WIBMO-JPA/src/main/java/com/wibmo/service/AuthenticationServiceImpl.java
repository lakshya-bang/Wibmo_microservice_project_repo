/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.wibmo.entity.User;
import com.wibmo.repository.AuthenticationRepository;

/**
 * 
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private AuthenticationRepository authenticationRepository;
	
	@Override
	public User login(String userEmail, String userPassword) {
		if(authenticationRepository.authenticate(userEmail, userPassword)) {
			// TODO: move to userOperation.getUserByEmail()
			return authenticationRepository
					.getUserDetails(userEmail);
		}
		return null;
	}

}
