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
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.service.UserServiceImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class UserServiceTest {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	/**
	 * junit for viewAccountsPendingForApproval
	 */
//	@Test
//	public void viewAccountsPendingForApprovalTest() {
//		
//	}
	
	/**
	 * junit test to add user
	 */
	//error not adding
	@Test
	public void addTest() {
		User testUser = new User(1013, "test@user.com", RegistrationStatus.PENDING, UserType.PROFESSOR);
		testUser.setPassword("test");
		try {
			userService.add(testUser);
		} catch (UserWithEmailAlreadyExistsException e) {
			e.printStackTrace();
		}
		boolean expectedRes = true;
		boolean actualRes = userService.isUserExistsById(1013);
		
		assertEquals(expectedRes, actualRes);
	}
	//error
	@Test
	public void add_shouldThrowExceptionTest() {
		User testUser = new User(1001, "abhi@stu.user.com", 
				RegistrationStatus.APPROVED, UserType.STUDENT);
		testUser.setPassword("abhi@123");
		
		assertThrows(UserWithEmailAlreadyExistsException.class, 
				()->userService.add(testUser));
	
	}
	
	/**
	 * Junit test for getUserIdByEmail
	 */
	@Test
	 public void getUserIdByEmailTest() {
		Integer expectedId = 1001;
		Integer actualId = userService.getUserIdByEmail("abhi@stu.user.com");
		
		assertEquals(expectedId, actualId);
	}
	/**
	 * test for updateAllPendingAccountRegistrationsTo
	 */
	@Test
	public void updateAllPendingAccountRegistrationsToTest() {
		boolean expectedResult = true;
		boolean actualResult = userService.updateAllPendingAccountRegistrationsTo(RegistrationStatus.APPROVED);
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * test for isUserExistsById
	 */
	@Test
	public void isUserExistsByIdTest() {
		boolean expectedResult = true;
		boolean actualResult = userService.isUserExistsById(1002);
		
		assertEquals(expectedResult, actualResult);
	}
}
