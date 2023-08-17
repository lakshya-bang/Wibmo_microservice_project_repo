/**
 * 
 */
package com.wibmo.dao;

import java.util.List;

/**
 * 
 */
public interface UserDAO {
	public List<Integer> view();
	
	public Boolean update(String status,int userId);
}
