package com.wibmo.business;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.dao.AdminDAO;
import com.wibmo.dao.AdminDAOImpl;

public class AdminOperationImpl implements AdminOperation {

	AdminDAO adminDAO = new AdminDAOImpl();
	
	@Override
	public Admin getAdminById(Long id) {
		return adminDAO.getById(id);
	}
	
	@Override
	public List<Admin> getAdminsByIds(Set<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addAdmins(List<Admin> admins) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationStatus acknowledgeStudentRegistration(Long registrationId, Integer semester) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCourseToCatalog(Course course) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignCoursesToProfessor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateReportCards() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateBills() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyNewRegistration() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyPendingDues() {
		// TODO Auto-generated method stub
		
	}

}
