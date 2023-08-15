package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.User;

public interface UserDAO {

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public List<User> findAllByIdIn(Set<Integer> ids);
}
