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
import com.wibmo.bean.Student;
import com.wibmo.service.AuthenticationService;
import com.wibmo.service.AuthenticationServiceImpl;
import com.wibmo.service.StudentOperation;
import com.wibmo.service.StudentOperationImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class StudentOperationTest {
	
	@Autowired
	private AuthenticationServiceImpl authenticationService;
	
	@Autowired
	private StudentOperationImpl studentOperation;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
//		studentOperation = new StudentOperationImpl();
//		authenticationService = new AuthenticationServiceImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		
		String email = "stest2@test.com";
		String password = "st@123";
		
		Student student = new Student(1,"stest2@test.com","stest2",1);
		
		studentOperation.add(student);
		Student checkStudent = studentOperation.getStudentById(authenticationService
				.login(email, password)
				.getUserId());
		assertEquals(student, checkStudent);
		
	}

}
