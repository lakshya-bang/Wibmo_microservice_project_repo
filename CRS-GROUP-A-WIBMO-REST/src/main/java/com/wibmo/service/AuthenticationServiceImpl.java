/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.wibmo.bean.User;
import com.wibmo.dao.AuthenticationDAO;
import com.wibmo.dao.AuthenticationDAOImpl;

/**
 * 
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	private AuthenticationDAOImpl authenticationDao;
	
	@Override
	public User login(String userEmail, String userPassword) {
		if(authenticationDao.authenticate(userEmail, userPassword)) {
			// TODO: move to userOperation.getUserByEmail()
			return authenticationDao
					.getUserDetails(userEmail);
		}
		return null;
	}

}
