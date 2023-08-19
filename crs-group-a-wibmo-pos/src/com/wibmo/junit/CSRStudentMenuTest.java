/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;

/**
 * 
 */
public class CSRStudentMenuTest {

	private AuthenticationService authenticationService;
	private StudentOperation studentOperation;
	
	@Before
	public void setup() {
		authenticationService = new AuthenticationServiceImpl();
		studentOperation = new StudentOperationImpl();
	}
	
	@Test
	public void loginTest() {
		String email = "abhi@stu.user.com";
		String password = "abhi@123";
		
		User user = authenticationService.login(email, password);
		
		assertNotNull(user);
	}
	
	@Test
	public void studentDetailsCorrectTest() {
		String email = "abhi@stu.upes.com";
		String password = "abhi@123";
		
		Student expectedStudent = new Student(
				1001,
				email,
				"Abhishek",
				1);
		
		Student actualStudent = studentOperation
							.getStudentById(
								authenticationService
									.login(email, password)
									.getUserId());
		
		assertEquals(expectedStudent, actualStudent);
	}
	
//	@Test
//	public void canViewCoursCatalogueTest() {
//		
//	}
}
