package com.wibmo.service;

import java.util.List;

import com.wibmo.entity.Admin;

/**
 * Defines the contracts to support all required Admin operations
 */
public interface AdminService {
	
	/**
	 * Fetches the Admin by the given Id
	 * 
	 * @param integer
	 * @return
	 */
	public Admin getAdminById(Integer integer);
	
	/**
	 * 
	 * @return
	 */
	public List<Admin> getAllAdmins();
	
	/**
	 * Adds the new admin into the Database
	 * 
	 * @param admin
	 */
	public void add(Admin admin);
	
}
