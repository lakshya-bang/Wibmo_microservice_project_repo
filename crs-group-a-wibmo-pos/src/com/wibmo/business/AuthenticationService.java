/**
 * 
 */
package com.wibmo.business;

/**
 * 
 */
public interface AuthenticationService {

	/**
	 * 
	 * TODO: should return a FailureReson (Invalid Password, User does not exist, etc.)
	 * TODO: should return UserDetails w.r.t. UserType of this user.
	 */
	public Boolean login(Long userId, String password);
}
