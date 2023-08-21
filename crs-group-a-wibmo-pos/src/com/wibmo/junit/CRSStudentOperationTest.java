/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Student;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;

/**
 * @author mourila.vatsav
 */
public class CRSStudentOperationTest {
	
	private AuthenticationService authenticationService;
	private StudentOperation studentOperation;

	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		studentOperation = new StudentOperationImpl();
		authenticationService = new AuthenticationServiceImpl();
		}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		String email = "stest2@test.com";
		String password = "qwe";
		
		Student student = new Student(5,"stest2@test.com","stest2",1);
		
		studentOperation.add(student);
		Student checkStudent = studentOperation.getStudentById(authenticationService
				.login(email, password)
				.getUserId());
		assertEquals(student, checkStudent);
		
	}

}
