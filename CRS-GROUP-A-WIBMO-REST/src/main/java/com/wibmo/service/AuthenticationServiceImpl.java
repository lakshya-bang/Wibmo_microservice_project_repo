/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.AuthenticationDAOImpl;
import com.wibmo.entity.User;

/**
 * 
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired UserServiceImpl userService;
	
	@Autowired
	private AuthenticationDAOImpl authenticationDao;
	
	@Override
	public User login(String userEmail, String userPassword) {
		if(authenticationDao.authenticate(userEmail, userPassword)) {
			return userService.getUserByEmail(userEmail);
		}
		return null;
	}

}
