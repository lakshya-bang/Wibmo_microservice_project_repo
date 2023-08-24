package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.bean.Admin;
import com.wibmo.dao.AdminDAOImpl;

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