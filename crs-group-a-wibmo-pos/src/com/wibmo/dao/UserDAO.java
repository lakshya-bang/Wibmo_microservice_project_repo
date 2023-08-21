/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.User;

/**
 * 
 */
public interface UserDAO {
	
	public List<Integer> view();
	
	public Boolean update(String status,int userId);

	/**
	 * Inserts the new user
	 * @param user
	 */
	public void save(User user);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public Integer findUserIdByEmail(String email);
}
