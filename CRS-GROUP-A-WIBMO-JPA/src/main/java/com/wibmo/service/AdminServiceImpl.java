package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.AdminDAOImpl;
import com.wibmo.entity.Admin;

/**
 * 
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAOImpl adminDAO;
	
	@Override
	public Admin getAdminById(int adminId) {
		return adminDAO.findById(adminId);
	}
	
	@Override
	public void add(Admin admin) {
		adminDAO.save(admin);
	}
	
}