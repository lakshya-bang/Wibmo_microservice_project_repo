package com.wibmo.business;

import com.wibmo.bean.Admin;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.dao.AdminDAO;
import com.wibmo.dao.AdminDAOImpl;

/**
 * 
 */
public class AdminOperationImpl implements AdminOperation {

	private final AdminDAO adminDAO;
	
	public AdminOperationImpl() {
		adminDAO = AdminDAOImpl.getInstance();
	}
	
	@Override
	public Admin getAdminById(int adminId) {
		return adminDAO.findById(adminId);
	}
	
	@Override
	public void add(Admin admin) {
		adminDAO.save(admin);
	}
	
}