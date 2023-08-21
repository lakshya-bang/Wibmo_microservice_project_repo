/**
 * 
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.bean.User;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

/**
 * 
 * @author mourila.vatsav
 * 
 */
public interface UserOperation {
	
	/**
	 * used for viewing the account that require approval
	 * @return List<Int> 
	 * */
	public List<Integer> viewAccountsPendingForApproval();
	
	/**
	 * used for approving the registration of the new user
	 * @param userId
	 * */
	public void approveLoginById(int userId);
	
	/**
	 * used for Rejecting the registration of the new user
	 * @param userId
	 * */
	public void rejectLoginById(int userId);

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
}
