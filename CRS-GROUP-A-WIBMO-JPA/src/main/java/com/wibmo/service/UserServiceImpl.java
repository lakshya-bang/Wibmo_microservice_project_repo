/**
 * @author mourila.vatsav
 */
package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.wibmo.converter.UserConverter;
import com.wibmo.dto.UserLogInDTO;
import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.dto.UserResponseDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.DepartmentCannotBeEmptyException;
import com.wibmo.exception.SemesterCannotBeEmptyException;
import com.wibmo.exception.UserNotAuthorizedForLogIn;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.repository.UserRepository;

/**
 * 
 */
@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	public AdminServiceImpl adminService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;

	@Override
	public UserResponseDTO getUserById(Integer userId) {
		Optional<User> userOptional = userRepository.findByUserId(userId);
		if(userOptional.isEmpty()) {
			return null;
		}
		return userConverter.convert(userOptional.get());
	}
	
	@Override
	public List<UserResponseDTO> getAllUsers() {
		return userConverter.convertAll(userRepository.findAll());
	}
	
	@Override
	public List<UserResponseDTO> getAccountsPendingForApproval() {
		return userConverter.convertAll(
				userRepository.findAllByRegistrationStatus(
						RegistrationStatus.PENDING));
	}
	
	@Override
	public void add(UserRegistrationDTO userRegistrationDTO) 
		throws 
			UserWithEmailAlreadyExistsException, 
			SemesterCannotBeEmptyException, 
			DepartmentCannotBeEmptyException {
		
		// TODO: Add Name Validation using Regex
		
		// TODO: Add Email Validation using Regex
		
		// TODO: Add Password Validation using Regex
		
		if(isEmailAlreadyInUse(
				userRegistrationDTO.getUserEmail())) {
			throw new UserWithEmailAlreadyExistsException(
					userRegistrationDTO.getUserEmail());
		}
		
		switch(userRegistrationDTO.getUserType()) {
		case STUDENT:
			if(userRegistrationDTO.getSemester() == null) {
				throw new SemesterCannotBeEmptyException();
			}
			break;
		case PROFESSOR:
			if(userRegistrationDTO.getDepartment() == null) {
				throw new DepartmentCannotBeEmptyException();
			}
			break;
		default:
		}
		
		User user = new User();
		user.setUserEmail(userRegistrationDTO.getUserEmail());
		user.setPassword(userRegistrationDTO.getPassword());
		user.setUserType(userRegistrationDTO.getUserType());
		user.setRegistrationStatus(RegistrationStatus.PENDING);
		
		LOG.info(user);
		
		userRepository.save(user);
		
		Integer userId = getUserIdByEmail(userRegistrationDTO.getUserEmail());
		
		LOG.info("userId: " + userId);
		
		// TODO: If userId is null throw Exception
		
		switch(user.getUserType()) {
		case ADMIN:
			Admin admin = new Admin();
			admin.setUserId(userId);
			admin.setAdminName(userRegistrationDTO.getUserName());
			admin.setAdminEmail(userRegistrationDTO.getUserEmail());
			
			LOG.info(admin);
			
			adminService.add(admin);
			break;
		case PROFESSOR:
			Professor professor = new Professor();
			professor.setUserId(userId);
			professor.setProfessorName(userRegistrationDTO.getUserName());
			professor.setProfessorEmail(userRegistrationDTO.getUserEmail());
			professor.setDepartment(userRegistrationDTO.getDepartment());
			
			LOG.info(professor);
			
			professorService.add(professor);
			break;
		case STUDENT:
			Student student = new Student();
			student.setUserId(userId);
			student.setStudentName(userRegistrationDTO.getUserName());
			student.setStudentEmail(userRegistrationDTO.getUserEmail());
			student.setCurrentSemester(userRegistrationDTO.getSemester());
			
			LOG.info(student);
			
			studentService.add(student);
		}
	}
	
	@Override
	public Integer getUserIdByEmail(String email) {
		return userRepository
			.findByUserEmail(email)
			.map(User::getUserId)
			.orElse(null);
	}
	
	@Override
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> userIds) 
					throws UserNotFoundException {
		
		if(null == userIds || userIds.isEmpty()) {
			return Boolean.FALSE;
		}
		
		
		List<User> users = userRepository.findAllByUserIdIn(userIds);
		
		if(users.size()!=userIds.size()) {
			return Boolean.FALSE;
		}
		users.forEach(user -> user.setRegistrationStatus(registrationStatus));
		
		userRepository.saveAll(users);
		
		return Boolean.TRUE;
		
	}
	
	@Override
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus) {
		
		List<User> pendingAccounts = userRepository
					.findAllByRegistrationStatus(
						RegistrationStatus.PENDING);
		
		pendingAccounts.forEach(
				account -> account
					.setRegistrationStatus(
						registrationStatus));
		
		userRepository.saveAll(pendingAccounts);
		
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean isUserExistsById(Integer userId) {
		return userRepository.existsById(userId);
	}
	
	@Override
	public UserResponseDTO getUserByEmail(String email) {
		Optional<User> userOptional = userRepository.findByUserEmail(email);
		if(!userOptional.isPresent()) {
			return null;
		}
		return userConverter.convert(userOptional.get());
	}
	
	@Override
	public void delete(Integer userId) throws UserNotFoundException {
		
		if(!isUserExistsById(userId)) {
			return;
		}
		
		User user = userRepository.findByUserId(userId).get();
		
		switch(user.getUserType()) {
		case PROFESSOR:
			// TODO: Check if this professor is assigned to teach any course.
			
			// If yes, throw exception.
			// Else, delete this professor.
			
			break;
		case STUDENT:
			// TODO: Check if this student has registered for any course.
			
			// TODO: Check if this student has any pending bills.
			
			// If yes, throw exception.
			// Else, delete this professor.
			break;
		default:
		}
		
	}
	
	@Override
	public RegistrationStatus getRegistrationStatusById(Integer userId) {
		// TODO: Should throw exception if user does not exist
		return userRepository
				.findById(userId)
				.get()
				.getRegistrationStatus();
	}
	
	public void logIn(UserLogInDTO userLoginDTO) 
			throws UserNotFoundException, UserNotAuthorizedForLogIn{	
		if(null == userLoginDTO) {
			return;
		}
		
		Optional<User> user = userRepository.findByUserEmail(userLoginDTO.getUserEmail());
		if(!user.isPresent()) {
			throw new UserNotFoundException(user.get().getUserId(), user.get().getUserType());
		} else if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(user.get().getRegistrationStatus())) {
			throw new UserNotAuthorizedForLogIn(userLoginDTO.getUserEmail());
		}
		else {
			if(userLoginDTO.getPassword().equals(user.get().getPassword()) ) {
				LOG.info("LogIn Successful.");
			}
			LOG.info("Email or Password is incorrect.");
		}
	}
	
	
	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return userRepository.existsByUserEmail(email);
	}

}
