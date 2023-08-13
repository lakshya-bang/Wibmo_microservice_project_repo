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

	User user1 = new User("Lakshya", 101L, "Bangalore", "lakshya@test.com", "Student", "abc");
	User user2 = new User("Lakshya", 102L, "India", "lakshya@test.com", "Professor", "abc");
	User user3 = new User("Lakshya", 103L, "Delhi", "lakshya@test.com", "Admin", "abc");
	Map<Long,Map<String,User>> fakeAuthDB = new HashMap<Long,Map<String,User>>();
	
	
	public FakeAuthenticationService() {
		fakeAuthDB.put(101L, Map.of("abc",user1));
		fakeAuthDB.put(102L, Map.of("abc",user2));
		fakeAuthDB.put(103L, Map.of("abc",user3));
	}
	
	@Override
	public User login(Long userId, String password) {
		if(fakeAuthDB.containsKey(userId)) {
			// TODO: Should return "User does not exists."
			return null;
		}
		if(!fakeAuthDB.get(userId).containsKey(password)) {
			// TODO: Should return "Incorrect password."
			return null;
		}
		return fakeAuthDB.get(userId).get(password);
	}

}
