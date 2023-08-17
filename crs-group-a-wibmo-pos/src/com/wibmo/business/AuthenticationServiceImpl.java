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
		
		// TODO: Should add Validator for type
		System.out.print("Enter User Id: ");
		Integer userId = in.nextInt();
		System.out.print("Enter Password: ");
		String password = in.next();
		
		if(AuthenticationDAOImpl.authenticate(userId, password)) {
			System.out.println("You have successfully logged in.....");
			System.out.println("Please wait while we fetch your details.....");
			System.out.println("====================================================");
			User user = AuthenticationDAOImpl.getUserDetails(userId);
			System.out.println("Details fetched successfully....");
			return user;
		}
		
		return null;
	}

}
