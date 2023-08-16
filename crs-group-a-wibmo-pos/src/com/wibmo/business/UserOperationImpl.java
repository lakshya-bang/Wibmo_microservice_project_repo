package com.wibmo.business;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.bean.User;
import com.wibmo.dao.UserDAO;
import com.wibmo.dao.UserDAOImpl;

public class UserOperationImpl implements UserOperation {

	private UserDAO userDAO = new UserDAOImpl();
	
	@Override
	public Map<Integer, User> getUserIdToUserMap(Set<Integer> userIds) {
		return userDAO
			.findAllByIdIn(userIds)
			.stream()
			.collect(Collectors.toMap(
					User::getUserId, 
					Function.identity()));
	}

}
