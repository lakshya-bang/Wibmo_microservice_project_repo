/**
 * 
 */
package com.wibmo.service;

import com.wibmo.bean.User;

/**
 * @author lakshya.bang
 */
public interface AuthenticationService {

	/**
	 * 
	 * TODO: should return a FailureReson (Invalid Password, User does not exist, etc.)
	 * TODO: should return UserDetails w.r.t. UserType of this user.
	 */
	
	/**
	 * 
	 * @param userEmail
	 * @param userPassword
	 * @return User
	 */
	public User login(String userEmail, String userPassword);

}
