/**
 * @author mourila.vatsav
 */
package com.wibmo.business;

import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.bean.User;
import com.wibmo.dao.UserDAO;


/**
 * 
 */

public class UserOperationImpl implements UserOperation{
	
	private final UserDAO userDAO;
	
	public UserOperationImpl() {
		userDAO = UserDAOImpl.getInstance();
	}

	@Override
	public void viewAccountsPendingForApproval() {
		 userDAO
		 	.findAllByRegistrationStatus(
		 			RegistrationStatus.PENDING)
		 	.stream()
		 	.forEach(System.out::println);
	}

	@Override
	public void add(User user) throws UserWithEmailAlreadyExistsException {
		
		if(null != user.getUserId()) {
			// TODO: Add to logger to reject the incoming request
			return;
		}
		
		if(isEmailAlreadyInUse(user.getUserEmail())) {
			throw new UserWithEmailAlreadyExistsException(user.getUserEmail());
		}
		
		userDAO.save(user);
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
	

	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return null != userDAO.findUserIdByEmail(email);
	}
}
