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
	public User login(String userName, String password) {
		
		if(AuthenticationDAOImpl.authenticate(userName, password)) {
			System.out.println("You have successfully logged in.....");
			System.out.println("Please wait while we fetch your details.....");
			System.out.println("====================================================");
			User user = AuthenticationDAOImpl.getUserDetails(userName);
			System.out.println("Details fetched successfully....");
			return user;
		}
		return null;
	}

}
