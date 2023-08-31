package com.wibmo.business;

import com.wibmo.bean.Admin;

/**
 * 
 */
public interface AdminOperation {
	
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
