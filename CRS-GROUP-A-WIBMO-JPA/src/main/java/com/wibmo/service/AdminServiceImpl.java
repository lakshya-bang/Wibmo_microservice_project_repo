package com.wibmo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Admin;
import com.wibmo.repository.AdminRepository;

/**
 * 
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public Admin getAdminById(Integer adminId) {
		Optional<Admin> adminOptional = adminRepository.findByAdminId(adminId);
		return adminOptional.isPresent()
				? adminOptional.get()
				: null;
	}
	
	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}
	
	@Override
	public void add(Admin admin) {
		
		if(null == admin) {
			return;
		}
		
		adminRepository.save(admin);
	}
	
}