/**
 * 
 */
package com.wibmo.business;

import com.wibmo.bean.User;

/**
 * 
 */
public interface AuthenticationService {

	/**
	 * 
	 * TODO: should return a FailureReson (Invalid Password, User does not exist, etc.)
	 * TODO: should return UserDetails w.r.t. UserType of this user.
	 */
	public User login(String userName, String password);

}
