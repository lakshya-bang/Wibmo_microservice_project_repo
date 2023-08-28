/**
 * @author mourila.vatsav
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.DepartmentCannotBeEmptyException;
import com.wibmo.exception.SemesterCannotBeEmptyException;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.repository.UserRepository;

/**
 * 
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	public AdminServiceImpl adminService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAccountsPendingForApproval() {
		return userRepository.findAllByRegistrationStatus(
				RegistrationStatus.PENDING);
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
		
		userRepository.save(user);
		
		Integer userId = getUserIdByEmail(user.getUserEmail());
		
		switch(user.getUserType()) {
		case ADMIN:
			adminService.add(new Admin(
					userId,
					user.getUserEmail(),
					userRegistrationDTO.getUserName()));
			break;
		case PROFESSOR:
			professorService.add(new Professor(
					userId,
					user.getUserEmail(),
					userRegistrationDTO.getUserName(),
					userRegistrationDTO.getDepartment()
					));
			break;
		case STUDENT:
			studentService.add(new Student(
					userId,
					user.getUserEmail(),
					userRegistrationDTO.getUserName(),
					userRegistrationDTO.getSemester()));
		}
	}
	
	@Override
	public Integer getUserIdByEmail(String email) {
		userRepository
			.findByUserEmail(email)
			.map(user -> {
				return user.getUserId();
			});
		return null;
	}
	
	@Override
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Collection<Integer> userIds) {
		
		if(null == userIds || userIds.isEmpty()) {
			return Boolean.FALSE;
		}
		
		List<User> users = userRepository.findAllByUserIdIn(userIds);
		
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
	public User getUserByEmail(String email) {
		Optional<User> userOptional = userRepository.findByUserEmail(email);
		return userOptional.isPresent()
				? userOptional.get()
				: null;
	}
	
	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return userRepository.existsByUserEmail(email);
	}

}
