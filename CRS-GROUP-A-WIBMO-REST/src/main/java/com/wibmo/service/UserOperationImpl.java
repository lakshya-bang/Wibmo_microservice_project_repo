/**
 * @author mourila.vatsav
 */
package com.wibmo.service;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.bean.User;
import com.wibmo.dao.UserDAO;


/**
 * 
 */
@Service
@Component
public class UserOperationImpl implements UserOperation{
	
//	private final UserDAO userDAO;
//	
	@Autowired
	UserDAOImpl userDAO;

	@Override //Will go in Admin Route
	public List<Integer> viewAccountsPendingForApproval() {
		// TODO Auto-generated method stub
			List<Integer> pendingAccounts = userDAO.find();
		return pendingAccounts;
	}

	@Override //Will go in Admin route
	public boolean approveLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userDAO.update("APPROVED", userId);
		return flag;
	}

	@Override //Will go in Admin route
	public boolean rejectLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userDAO.update("REJECTED", userId);
		return flag;
		
	}

	@Override //Will go through user route.
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
	
	
	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return null != userDAO.findUserIdByEmail(email);
	}
	
	@Override
	public Integer getUserIdByEmail(String email) {
		return userDAO.findUserIdByEmail(email);
	}


}
