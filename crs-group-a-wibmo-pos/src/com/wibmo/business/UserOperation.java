/**
 * 
 */
package com.wibmo.business;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

/**
 * 
 * @author mourila.vatsav
 * 
 */
public interface UserOperation {
	
	/**
	 * Used for viewing the accounts that require approval from Admin
	 * @return List<Integer> 
	 * */
	public void viewAccountsPendingForApproval();
	
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
	
}
