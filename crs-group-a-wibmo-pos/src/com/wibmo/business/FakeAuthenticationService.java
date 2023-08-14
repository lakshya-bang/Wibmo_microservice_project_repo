/**
 * 
 */
package com.wibmo.business;

import java.util.HashMap;
import java.util.Map;

import com.wibmo.bean.User;

/**
 * 
 */
public class FakeAuthenticationService implements AuthenticationService {

	static private User user1 = new User("Lakshya", 101L, "Bangalore", "lakshya1@test.com", "Student", 900000L);
	static private User user2 = new User("Lakshya", 102L, "India", "lakshya2@test.com", "Professor", 900000L);
	static private User user3 = new User("Lakshya", 103L, "Delhi", "lakshya3@test.com", "Admin", 900000L);
	static Map<String,Map<String,User>> fakeAuthDB = new HashMap<>();
	
	public FakeAuthenticationService() {
		
	}
	public User login(String username, String password) {
		fakeAuthDB.put(user1.getUserEmail(), Map.of("abc",user1));
		fakeAuthDB.put(user2.getUserEmail(), Map.of("abc",user2));
		fakeAuthDB.put(user3.getUserEmail(), Map.of("abc",user3));	
		
		if(!fakeAuthDB.containsKey(username)) {
			// TODO: Should return "User does not exists."
			return null;
		}
		
		if(!fakeAuthDB.get(username).containsKey(password)) {
			// TODO: Should return "Incorrect password."
			return null;
		}
		return fakeAuthDB.get(username).get(password);
	}
	@Override
	public User login() {
		// TODO Auto-generated method stub
		return null;
	}

}
