package com.wibmo.dao;

import com.wibmo.bean.Admin;

/**
 * 
 */
public interface AdminDAO {

	/**
	 * 
	 * @param adminId
	 * @return
	 */
	public Admin findById(Integer adminId);

	/**
	 * 
	 * @param admin
	 */
	public void save(Admin admin);

}