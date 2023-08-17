/**
 * @author mourila.vatsav
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.dao.UserDAOimpl;
import com.wibmo.dao.UserDAO;


/**
 * 
 */

public class UserOperationimpl implements UserOperation{
	UserDAO userDAO = new UserDAOimpl();

	@Override
	public List<Integer> viewAccountsPendingForApproval() {
		// TODO Auto-generated method stub
			List<Integer> pendingAccounts = userDAO.view();
		return pendingAccounts;
	}

	@Override
	public void approveLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userDAO.update("APPROVED", userId);
		if(flag) {
			System.out.println("the user with the Id" + userId + " has been Approved");
		}
	}

	@Override
	public void rejectLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userDAO.update("REJECTED", userId);
		if(flag) {
			System.out.println("the user with the Id" + userId + " has been Rejected");
		}
		
	}
	

}
