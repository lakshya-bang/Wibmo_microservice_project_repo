/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;
import com.wibmo.entity.User;
import com.wibmo.service.AuthenticationServiceImpl;
//import com.wibmo.service.AuthenticationServiceImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class AuthenticationServiceTest {
	
	@Autowired
	private AuthenticationServiceImpl authenticationService;
	/**
	 * @throws java.lang.Exception
	 */
//	@BeforeEach
//	void setUp() throws Exception {
//		authenticationService = new AuthenticationServiceImpl();
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterEach
//	void tearDown() throws Exception {
//	}
	
	@Test
	public void loginSuccess() {
//		User user = null;
		User user = authenticationService.login("abhi@stu.user.com", "abhi@123");
		assertNotNull(user);
	}
	
	@Test
	public void loginFailure() {
//		User user = null;
		User user = authenticationService.login("abc", "abc");
		assertNull(user);
	}

}
