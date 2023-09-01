/**
 * 
 */
package com.wibmo.repository;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.wibmo.converter.UserConverter;
import com.wibmo.dto.UserResponseDTO;
import com.wibmo.entity.Professor;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.service.UserServiceImpl;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class TestUserDAO {
	
	
	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserConverter userConverter;
	
	@Mock
	UserRepository userRepository;
	

	@Test
	void getUserById_WhenUserIsNotFound_ShouldReturnNullTest() {
		Optional<User> userOptional = Optional.empty();
		
		UserResponseDTO expectedUserDTOOptional = null;
		
		Integer userId=1;
		
		when(userRepository.findByUserId(any(Integer.class))).thenReturn(userOptional);
		
//		when(userConverter.convert(any(User.class))).thenReturn(userDTOOptional);
		
		UserResponseDTO actualUserResponseDTO = userService.getUserById(userId);
		
		assertNull(actualUserResponseDTO);
		assertEquals(expectedUserDTOOptional, actualUserResponseDTO);
	}
	
	@Test
	void getUserById_WhenUserIsFound_ShouldReturnUserTest() {
		Optional<User> userOptional = Optional.of(
					new User(
								new Random().nextInt(),
								"abc@gmail.com",
								UserType.STUDENT,
								RegistrationStatus.APPROVED
							)
				);
		
		UserResponseDTO expectedUserDTOOptional = new UserResponseDTO(
					userOptional.get().getUserId(),
					userOptional.get().getUserEmail(),
					userOptional.get().getUserType(),
					userOptional.get().getRegistrationStatus()
				);
		
		Integer userId=1;
		
		when(userRepository.findByUserId(any(Integer.class))).thenReturn(userOptional);
		
		when(userConverter.convert(any(User.class))).thenReturn(expectedUserDTOOptional);
		
		UserResponseDTO actualUserResponseDTO = userService.getUserById(userId);
		
		assertNotNull(actualUserResponseDTO);
		assertEquals(expectedUserDTOOptional, actualUserResponseDTO);
	}

	@Test
	void getAllUsers() {
		List<User> expectedUser = List.of(
				new User(
						new Random().nextInt(),
						new String(),
						UserType.ADMIN,
						RegistrationStatus.APPROVED),
				new User(
						new Random().nextInt(),
						new String(),
						UserType.ADMIN,
						RegistrationStatus.APPROVED));
		
		List<UserResponseDTO> expectedUserDTO = List.of(
				new UserResponseDTO(
						expectedUser.get(0).getUserId(),
						expectedUser.get(0).getUserEmail(),
						UserType.ADMIN,
						RegistrationStatus.APPROVED),
				new UserResponseDTO(
						expectedUser.get(1).getUserId(),
						expectedUser.get(1).getUserEmail(),
						UserType.ADMIN,
						RegistrationStatus.APPROVED)
				);
		when(userRepository.findAll()).thenReturn(expectedUser);
		when(userConverter.convertAll(expectedUser)).thenReturn(expectedUserDTO);
		List<UserResponseDTO> actualUserDTO = userService.getAllUsers();
		
		assertNotNull(actualUserDTO);
		assertEquals(expectedUserDTO.size(),actualUserDTO.size());
		assertEquals(expectedUserDTO, actualUserDTO);
		
	}
	
	@Test
	void getAccountsPendingForApproval(){
		List<User> expectedUser= List.of(
				new User(
						new Random().nextInt(),
						new String(),
						UserType.ADMIN,
						RegistrationStatus.PENDING),
				new User(
						new Random().nextInt(),
						new String(),
						UserType.ADMIN,
						RegistrationStatus.APPROVED)
				);
		List<UserResponseDTO> expectedUserResponseDTO=List.of(
				new UserResponseDTO(
						expectedUser.get(0).getUserId(),
						expectedUser.get(0).getUserEmail(),
						UserType.ADMIN,
						RegistrationStatus.PENDING),
				new UserResponseDTO(
						expectedUser.get(1).getUserId(),
						expectedUser.get(1).getUserEmail(),
						UserType.ADMIN,
						RegistrationStatus.APPROVED)
				);
		when(userRepository.findAllByRegistrationStatus(RegistrationStatus.PENDING)).thenReturn(expectedUser);
		when(userConverter.convertAll(expectedUser)).thenReturn(expectedUserResponseDTO);
		
		List<UserResponseDTO> actualUserResponseDTO = userService.getAccountsPendingForApproval();
		assertNotNull(actualUserResponseDTO);
		assertEquals(expectedUserResponseDTO.size(), actualUserResponseDTO.size());
		assertEquals(expectedUserResponseDTO, actualUserResponseDTO);

	}
	
	@Test
	void getUserIdByEmail_WhenUserIsNotFound_ShouldReturnNullTest() {
Optional<User> userOptional = Optional.empty();
		
		UserResponseDTO expectedUserDTOOptional = null;
		
		String userEmail="A";
		
		when(userRepository.findByUserEmail(any(String.class))).thenReturn(userOptional);
		
//		when(userConverter.convert(any(User.class))).thenReturn(userDTOOptional);
		
		Integer actualuserID = userService.getUserIdByEmail(userEmail);
		
		assertNull(actualuserID);
	}
	
	@Test
	void getUserIdByEmail_WhenUserIsFound_ShouldReturnUserTest() {
		Optional<User> userOptional = Optional.of(
					new User(
								1,
								new String(),
								UserType.STUDENT,
								RegistrationStatus.APPROVED
							)
				);
		
		UserResponseDTO expectedUserDTO = new UserResponseDTO(
					userOptional.get().getUserId(),
					userOptional.get().getUserEmail(),
					userOptional.get().getUserType(),
					userOptional.get().getRegistrationStatus()
				);
		
		String userEmail="A";
		
		when(userRepository.findByUserEmail(any(String.class))).thenReturn(userOptional);
		
		
		Integer actualUserId = userService.getUserIdByEmail(userEmail);
		
		assertNotNull(actualUserId);
		assertEquals(expectedUserDTO.getUserId(), actualUserId);
	}
	
	@Test
	void getUserByEmail_WhenUserIsNotFound_ShouldReturnNullTest() {
		Optional<User> userOptional = Optional.empty();
		
		UserResponseDTO expectedUserDTOOptional = null;
		
		String userEmail="A";
		
		when(userRepository.findByUserEmail(any(String.class))).thenReturn(userOptional);
		

		UserResponseDTO actualUserResponseDTO = userService.getUserByEmail(userEmail);
		
		assertNull(actualUserResponseDTO);
		assertEquals(expectedUserDTOOptional, actualUserResponseDTO);
	}
	
	@Test
	void getUserByEmail_WhenUserIsFound_ShouldReturnUserTest() {
		Optional<User> userOptional = Optional.of(
				new User(
							new Random().nextInt(),
							"abc@gmail.com",
							UserType.STUDENT,
							RegistrationStatus.APPROVED
						)
			);
	
	UserResponseDTO expectedUserDTOOptional = new UserResponseDTO(
				userOptional.get().getUserId(),
				userOptional.get().getUserEmail(),
				userOptional.get().getUserType(),
				userOptional.get().getRegistrationStatus()
			);
	
	String userEmail="A";
	
	when(userRepository.findByUserEmail(any(String.class))).thenReturn(userOptional);
	
	when(userConverter.convert(any(User.class))).thenReturn(expectedUserDTOOptional);
	
	UserResponseDTO actualUserResponseDTO = userService.getUserByEmail(userEmail);
	
	assertNotNull(actualUserResponseDTO);
	assertEquals(expectedUserDTOOptional, actualUserResponseDTO);
	}
	
	@Test
	void getRegistrationStatusById_WhenUserIsNotFound_ShouldReturnNullTest(){
		Optional<User> userOptional = Optional.empty();
		
		UserResponseDTO expectedUserDTOOptional = null;
		
		Integer userId=1;
		
		when(userRepository.findByUserId(1)).thenReturn(userOptional);
		

		UserResponseDTO actualUserResponseDTO = userService.getUserById(userId);
		
		assertNull(actualUserResponseDTO);
		assertEquals(null, actualUserResponseDTO);
		
	}
	
	@Test
	void getRegistrationStatusById_WhenUserIsFound_ShouldReturnUserTest(){
		Optional<User> userOptional = Optional.of(
				new User(
							1,
							"abc@gmail.com",
							UserType.STUDENT,
							RegistrationStatus.APPROVED
						)
			);
		Optional<User> userOptional1 = Optional.of(
				new User(
							1,
							"abc@gmail.com",
							UserType.STUDENT,
							RegistrationStatus.APPROVED
						)
			);
	UserResponseDTO expectedUserDTOOptional = new UserResponseDTO(
				userOptional.get().getUserId(),
				userOptional.get().getUserEmail(),
				userOptional.get().getUserType(),
				userOptional.get().getRegistrationStatus()
			);
	
	Integer userId=1;
	
	when(userRepository.findByUserId(any(Integer.class))).thenReturn(userOptional);
	
	when(userConverter.convert(any(User.class))).thenReturn(expectedUserDTOOptional);
	
	UserResponseDTO actualUserResponseDTO = userService.getUserById(userId);
	
	assertNotNull(actualUserResponseDTO);
	assertEquals(expectedUserDTOOptional,actualUserResponseDTO);
	}
	
}
