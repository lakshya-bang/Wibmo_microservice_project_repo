package com.wibmo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.entity.Admin;
import com.wibmo.repository.AdminRepository;

/**
 * 
 */

public class AdminServiceImpl implements AdminService {

	private AdminRepository adminRepository;
	
	@Override
	public Optional<Admin> getAdminById(int adminId) {
		return adminRepository.findById(adminId);
	}
	
	@Override
	public void add(Admin admin) {
		adminRepository.save(admin);
	}
	
}