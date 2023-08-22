/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.AuthenticationServiceImpl;

/**
 * 
 */
public class AuthenticationServiceTest {

	private static AuthenticationService authenticationService;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		authenticationService = new AuthenticationServiceImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loginSuccess() {
		User user = null;
		user = authenticationService.login("test", "test");
		assertNotNull(user);
	}
	
	@Test
	public void loginFailure() {
		User user = null;
		user = authenticationService.login("abc", "abc");
		assertNull(user);
	}

}
