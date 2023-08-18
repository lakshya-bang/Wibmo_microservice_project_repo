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
		
		Scanner input = new Scanner(System.in);
		
		// TODO: Should add Validator for type
		System.out.print("Enter User Id: ");
		
		while(!input.hasNextInt());
		
		Integer userId = input.nextInt();
		System.out.print("Enter Password: ");
		String password = input.next();
	
		if(AuthenticationDAOImpl.authenticate(userId, password)) {
			return AuthenticationDAOImpl
					.getUserDetails(userId);
		}
		return null;
	}

}
