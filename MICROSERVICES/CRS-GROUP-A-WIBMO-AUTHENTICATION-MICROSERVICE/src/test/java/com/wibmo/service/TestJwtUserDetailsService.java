/**
 * 
 */
package com.wibmo.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.User;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.repository.AuthenticationRepository;
import com.wibmo.utils.UserRegistrationUtil;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class TestJwtUserDetailsService {

	@InjectMocks
	JwtUserDetailsService userDetailsService;
	
	@Mock
	AuthenticationRepository authenticationRepository;
	
	@Mock
	UserRegistrationUtil userRegistrationUtil;
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
	void loadUserByUsername_when_user_null() {
		when(authenticationRepository.findByuserEmail(Mockito.anyString())).thenReturn(null);
		try {
		userDetailsService.loadUserByUsername("test");
		}
		catch(Exception e) {
			assertTrue(e.getMessage().contains("User not found"));
		}
	}
	
	@Test
	void loadUserByUsername_when_user_not_null() {
		User user = new User();
		user.setUserEmail("test");
		user.setPassword("test");
		user.setUserId(1);
		org.springframework.security.core.userdetails.User expectedUser = new org.springframework.security.core.userdetails.User(user.getUserEmail(),user.getPassword(),new HashSet<SimpleGrantedAuthority>());
		when(authenticationRepository.findByuserEmail(Mockito.anyString())).thenReturn(user);
		try {
			UserDetails actualUser = userDetailsService.loadUserByUsername("test");
			assertEquals(expectedUser.getUsername(),actualUser.getUsername());
			assertEquals(expectedUser.getPassword(),actualUser.getPassword());
		}
		catch(Exception e) {
		}
	}
	
	@Test 
	void save_failure() {
		UserRegistrationDTO user = null;
		try {
			when(userRegistrationUtil.saveRegDetailsUser(user)).thenThrow(new UserWithEmailAlreadyExistsException(null));
		} catch (UserWithEmailAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			userDetailsService.save(user);
		}catch(Exception e) {
			assertTrue(e.getMessage().contains("User with Email"));
		}
	}
	
	@Test 
	void save_success() {
		UserRegistrationDTO user = null;
		try {
			when(userRegistrationUtil.saveRegDetailsUser(user)).thenReturn(new User());
		} catch (UserWithEmailAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			User actualUser = userDetailsService.save(user);
			assertNotNull(actualUser);
		}catch(Exception e) {
			
		}
	}
}
