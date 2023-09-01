package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.*;

 

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.service.AdminService;
import com.wibmo.service.StudentService;
import com.wibmo.service.AdminServiceImpl;

 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

 

@ExtendWith(MockitoExtension.class)
class TestAdminDAO {

     @InjectMocks
     AdminServiceImpl adminService;

     @Mock
     AdminRepository adminRepository;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }
    
    @Test
    void findAllAdmin() {
         List<Admin> list = new ArrayList<Admin>();
            Admin adminOne = new Admin();
            Admin adminTwo = new Admin();
            adminOne.setAdminName("bob123@gmail.com");
            adminTwo.setAdminEmail("bob123@gmail.com");

            adminOne.setAdminName("RC");
            adminTwo.setAdminEmail("RC");

            list.add(adminOne);
            list.add(adminTwo);

            when(adminRepository.findAll()).thenReturn(list);
            List<Admin> empList = adminService.getAllAdmins();

            assertEquals(2, empList.size());

    }

    @Test
    void getAdminById_WhenAdminNotFound_ShouldReturnNullTest() {
    	Optional<Admin> dummyAdminOptional =Optional.empty();

    	
    	Integer adminId=1;
    	when(adminRepository.findByAdminId(adminId)).thenReturn(dummyAdminOptional);
    	Admin admin = adminService.getAdminById(adminId);
    	assertEquals(null, admin);
    }
    
    @Test
    void getAdminById_WhenAdminisFound_ShouldReturnAdminTest() {
    	Optional<Admin> dummyAdminOptional = Optional.of(
    			new Admin(
    						3000,					//adminId
    						new Random().nextInt(), //userId
    						"abc@gmail.com",		//adminEmail
    						"abc"					//adminName
    					)
    			);
    			when(adminRepository.findByAdminId(any(Integer.class)))
    			.thenReturn(dummyAdminOptional);
    			
    			Admin admin=adminService.getAdminById(3000);
    			
    			assertNotNull(admin);
    			assertEquals(dummyAdminOptional.get(), admin);
    			
    }

}
