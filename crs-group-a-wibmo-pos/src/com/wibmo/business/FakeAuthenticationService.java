/**
 * 
 */
package com.wibmo.business;

import java.util.Map;

/**
 * 
 */
public class FakeAuthenticationService implements AuthenticationService {

	Map<Long, String> fakeAuthDB = Map.of(
				1001L, "1234", 
				1002L, "root", 
				1003L, "password");

	
	@Override
	public Boolean login(Long userId, String password) {
		if(fakeAuthDB.containsKey(userId)) {
			// TODO: Should return "User does not exists."
			return false;
		}
		if(!password.equals(fakeAuthDB.get(userId))) {
			// TODO: Should return "Incorrect password."
			return false;
		}
		return true;
	}

}
