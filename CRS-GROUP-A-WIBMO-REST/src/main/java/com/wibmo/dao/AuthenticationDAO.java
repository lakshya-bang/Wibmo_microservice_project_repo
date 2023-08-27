/**
 * 
 */
package com.wibmo.dao;

/**
 * 
 */
public interface AuthenticationDAO {

	/**
	 * 
	 * @param userEmail
	 * @param password
	 * @return
	 */
    public boolean authenticate(String userEmail, String password);

}
