/**
 * @author mourila.vatsav
 */
package com.wibmo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.repository.UserRepository;

/**
 * 
 */

public class UserServiceImpl implements UserService{
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	public AdminServiceImpl adminService;
	
	private UserRepository userRepository;

	@Override
	public List<User> viewAccountsPendingForApproval() {
			// TODO Auto-generated method stub
		List<User> pendingAccounts = userRepository.findAllByRegistrationStatus(RegistrationStatus.PENDING);
			return pendingAccounts;
		}
	
	@Override //Will go in Admin route
	public boolean approveLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userRepository.update("APPROVED", userId);
		return flag;
	}

	@Override //Will go in Admin route
	public boolean rejectLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userRepository.update("REJECTED", userId);
		return flag;
		
	}

	@Override
	public void add(User user) 
			throws UserWithEmailAlreadyExistsException {
		
		if(null != user.getUserId()) {
			// TODO: Add to logger to reject the incoming request
			return;
		}
		
		if(isEmailAlreadyInUse(user.getUserEmail())) {
			throw new UserWithEmailAlreadyExistsException(user.getUserEmail());
		}
		
		userRepository.save(user);
		
//		switch(user.getUserType()) {
//		case STUDENT:
//			studentService.add(
//				new Student(
//					getUserIdByEmail(user.getUserEmail()),
//					user.getUserEmail(),
//					user.get));
//			break;
//		case ADMIN:
//			break;
//		case PROFESSOR:
//			break;
//		}
	}
	
	@Override
	public Integer getUserIdByEmail(String email) {
		return userRepository.findUserIdByEmail(email);
	}
	
	@Override
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Set<Integer> userIds) {
		
		if(null == userIds || userIds.isEmpty()) {
			return Boolean.FALSE;
		}
		
		return userRepository.updateRegistrationStatusAsByIdIn(
								registrationStatus, userIds);
		
	}
	
	@Override
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus) {
		
		return userRepository.updateRegistrationStatusAsByIdIn(
				registrationStatus,
				userRepository
					.findAllByRegistrationStatus(
						RegistrationStatus.PENDING)
					.stream()
					.map(user -> user.getUserId())
					.collect(Collectors.toSet()));
	}
	
	@Override
	public Boolean isUserExistsById(Integer userId) {
		return userRepository.existsById(userId);
	}
	

	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return null != userRepository.findUserIdByEmail(email);
	}
}
