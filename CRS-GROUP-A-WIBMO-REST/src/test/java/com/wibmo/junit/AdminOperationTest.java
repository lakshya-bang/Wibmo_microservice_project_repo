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
import com.wibmo.bean.Admin;
import com.wibmo.service.AdminOperationImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class AdminOperationTest {
	
	@Autowired
	private AdminOperationImpl adminOperation;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void Addtest() {
		Admin admin = new Admin(101, "admin@test.com", "testAdmin");
		adminOperation.add(admin);
		Admin checkAdmin = adminOperation.getAdminById(101);
		
		assertEquals(admin, checkAdmin);
	}

}
