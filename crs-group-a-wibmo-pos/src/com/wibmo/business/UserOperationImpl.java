/**
 * @author mourila.vatsav
 */
package com.wibmo.business;

import java.util.List;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.bean.User;
import com.wibmo.dao.UserDAO;


/**
 * 
 */

public class UserOperationImpl implements UserOperation{
	
	UserDAO userDAO = new UserDAOImpl();

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

	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return null != userDAO.findUserIdByEmail(email);
	}
}
