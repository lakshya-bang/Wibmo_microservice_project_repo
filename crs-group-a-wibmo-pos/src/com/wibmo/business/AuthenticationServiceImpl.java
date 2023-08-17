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
	public User login() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter your email id: ");
		String user_name = in.next();
		System.out.println("Please enter your password: ");
		String password = in.next();
		if(AuthenticationDAOImpl.authenticate(user_name, password)) {
			System.out.println("You have successfully logged in.....");
			System.out.println("Please wait while we fetch your details.....");
			System.out.println("====================================================");
			User user = AuthenticationDAOImpl.getUserDetails(user_name);
			System.out.println("Details fetched successfully....");
			return user;
		}
		in.close();
		return null;
	}

}
