/**
 * @author mourila.vatsav
 */
package com.wibmo.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

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
	private UserDAOImpl userDAO;

	@Override
	public void viewAccountsPendingForApproval() {
		 userDAO
		 	.findAllByRegistrationStatus(
		 			RegistrationStatus.PENDING)
		 	.stream()
		 	.forEach(System.out::println);
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
		
		userDAO.save(user);
		
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
		return userDAO.findUserIdByEmail(email);
	}
	
	@Override
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Set<Integer> userIds) {
		
		if(null == userIds || userIds.isEmpty()) {
			return Boolean.FALSE;
		}
		
		return userDAO.updateRegistrationStatusAsByIdIn(
								registrationStatus, userIds);
		
	}
	
	@Override
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus) {
		
		return userDAO.updateRegistrationStatusAsByIdIn(
				registrationStatus,
				userDAO
					.findAllByRegistrationStatus(
						RegistrationStatus.PENDING)
					.stream()
					.map(user -> user.getUserId())
					.collect(Collectors.toSet()));
	}
	
	@Override
	public Boolean isUserExistsById(Integer userId) {
		return userDAO.existsById(userId);
	}
	

	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return null != userDAO.findUserIdByEmail(email);
	}
}
