package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Admin;

public interface AdminDAO {

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public List<Admin> getByIdIn(Set<Long> ids);
	
	/**
	 * 
	 * @param admin
	 * @return the number of rows affected
	 */
	public Integer persist(List<Admin> admins);
	
	public Integer update(List<Admin> admins);
	
	public Integer removeByIdIn(Set<Long> ids);
	
}
