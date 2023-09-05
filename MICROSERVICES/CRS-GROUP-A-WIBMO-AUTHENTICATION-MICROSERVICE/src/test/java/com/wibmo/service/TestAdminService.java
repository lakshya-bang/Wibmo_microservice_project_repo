/**
 * 
 */
package com.wibmo.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.repository.AdminRepository;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class TestAdminService {

	 @InjectMocks
     AdminServiceImpl adminService;

     @Mock
     AdminRepository adminRepository;
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
	void testAdd_when_admin_null() {
		Admin admin = null;
		adminService.add(admin);
		verify(adminRepository, times(0))
		.save(any(Admin.class));
		
	}
	
	@Test
	void testAdd_when_admin_not_null() {
		Admin admin = new Admin();
		adminService.add(admin);
		verify(adminRepository, times(1))
		.save(any(Admin.class));
	}

}
