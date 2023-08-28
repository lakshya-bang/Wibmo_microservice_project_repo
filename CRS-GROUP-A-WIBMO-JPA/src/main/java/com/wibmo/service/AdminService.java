package com.wibmo.service;

import java.util.Optional;

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
	public Optional<Admin> getAdminById(int integer);
	
	/**
	 * 
	 * @param admin
	 */
	public void add(Admin admin);
}
