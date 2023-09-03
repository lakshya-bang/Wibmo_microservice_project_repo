package com.wibmo.service;

import java.util.List;

import com.wibmo.entity.Admin;

/**
 * Defines the contracts to support all required Admin operations
 */
public interface AdminService {
	
	/**
	 * Adds the new admin into the Database
	 * 
	 * @param admin
	 */
	public void add(Admin admin);
	
}
