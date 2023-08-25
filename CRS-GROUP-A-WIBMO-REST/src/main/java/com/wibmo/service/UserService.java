/**
 * 
 */
package com.wibmo.service;

import java.util.List;
import java.util.Set;

import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

/**
 * 
 * @author mourila.vatsav
 * 
 */
public interface UserService {
	
	/**
	 * Used for viewing the accounts that require approval from Admin
	 * @return List<Integer> 
	 * */
	public List<User> viewAccountsPendingForApproval();
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserWithEmailAlreadyExistsException 
	 */
	public void add(User user) throws UserWithEmailAlreadyExistsException;
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public Integer getUserIdByEmail(String email);

	/**
	 * 
	 * @param approved
	 * @param userIds
	 */
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus, 
			Set<Integer> userIds);

	/**
	 * 
	 * @param approved
	 */
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Boolean isUserExistsById(Integer userId);
	
	/**
	 * used for approving the registration of the new user
	 * @param userId
	 * */
	public boolean approveLoginById(int userId);
	
	/**
	 * used for Rejecting the registration of the new user
	 * @param userId
	 * */
	public boolean rejectLoginById(int userId);
	
}
