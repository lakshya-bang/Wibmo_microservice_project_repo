package com.wibmo.business;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.dao.AdminDAO;
import com.wibmo.dao.AdminDAOImpl;

public class AdminOperationImpl implements AdminOperation {

	AdminDAO adminDAO = new AdminDAOImpl();
	
	@Override
	public RegistrationStatus acknowledgeStudentRegistration(Long registrationId, Integer semester) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acknowledgeStudentRegistration'");
	}

	@Override
	public Admin getAdminById(Integer integer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getAdminById'");
	}

	@Override
	public boolean registerAdmin(String email, String password, String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registerAdmin'");
	}

	// @Override
	// public boolean registerAdmin(String email, String password,String name) {
	// 	// TODO Auto-generated method stub
	// 	return adminDAO.saveAdmin(email,password,name); 
	// 	// throw new UnsupportedOperationException("Unimplemented method 'addAdmins'");
	// }

	// @Override
	// public boolean addCourseToCatalog(Course course) {
	// 	// TODO Auto-generated method stub
	// 	// Course course = new Course();
	// 	return adminDAO.saveCourse(course); 
	// 	// throw new UnsupportedOperationException("Unimplemented method 'addCourseToCatalog'");
	// }

	// @Override
	// public boolean dropCourseFromCatalog(String courseName) {
	// 	// TODO Auto-generated method stub
	// 	adminDAO.deleteCourse(courseName);
	// 	throw new UnsupportedOperationException("Unimplemented method 'dropCourseFromCatalog'");
	// }

	// @Override
	// public void assignCoursesToProfessor(String courseName,String professorName) {
	// 	// TODO Auto-generated method stub
	// 	adminDAO.assignCoursesToProfessor(courseName, professorName);
	// 	throw new UnsupportedOperationException("Unimplemented method 'assignCoursesToProfessor'");
	// }

	// @Override
	// public void generateReportCards() {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'generateReportCards'");
	// }

	// @Override
	// public Admin getAdminById(Integer integer) {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }
	
	

}
