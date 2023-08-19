/**
 * 
 */
package com.wibmo.business;

import java.util.Scanner;

import com.wibmo.bean.User;
import com.wibmo.dao.AuthenticationDAOImpl;

/**
 * 
 */
public class AuthenticationServiceImpl implements AuthenticationService{

	@Override
	public User login(String userEmail, String userPassword) {
		if(AuthenticationDAOImpl.authenticate(userEmail, userPassword)) {
			return AuthenticationDAOImpl
					.getUserDetails(userEmail);
		}
		return null;
	}

}
