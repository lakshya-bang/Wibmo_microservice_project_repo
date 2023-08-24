package com.wibmo.service;

import com.wibmo.entity.Admin;

/**
 * 
 */
public interface AdminService {
	
	/**
	 * 
	 * @param integer
	 * @return
	 */
	public Admin getAdminById(int integer);
	
	/**
	 * 
	 * @param admin
	 */
	public void add(Admin admin);
}
