/**
 * 
 */
package com.wibmo.business;

import java.util.List;

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
}
