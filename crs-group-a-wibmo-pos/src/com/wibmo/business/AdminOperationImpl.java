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
	AuthenticationService authenticationServiceImpl=  new AuthenticationServiceImpl();

	@Override
	public boolean logIn() {
		// TODO Auto-generated method stub
		authenticationServiceImpl.login();
		throw new UnsupportedOperationException("Unimplemented method 'logIn'");
	}

	@Override
	public boolean registerAdmin(String email, String password,String name) {
		// TODO Auto-generated method stub
		return adminDAO.saveAdmin(email,password,name); 
		// throw new UnsupportedOperationException("Unimplemented method 'addAdmins'");
	}

	@Override
	public RegistrationStatus acknowledgeStudentRegistration(Long registrationId, Integer semester) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acknowledgeStudentRegistration'");
	}

	@Override
	public boolean addCourseToCatalog(Course course) {
		// TODO Auto-generated method stub
		// Course course = new Course();
		return adminDAO.saveCourse(course); 
		// throw new UnsupportedOperationException("Unimplemented method 'addCourseToCatalog'");
	}

	@Override
	public boolean dropCourseFromCatalog(String courseName) {
		// TODO Auto-generated method stub
		adminDAO.deleteCourse(courseName);
		throw new UnsupportedOperationException("Unimplemented method 'dropCourseFromCatalog'");
	}

	@Override
	public void assignCoursesToProfessor(String courseName,String professorName) {
		// TODO Auto-generated method stub
		adminDAO.assignCoursesToProfessor(courseName, professorName);
		throw new UnsupportedOperationException("Unimplemented method 'assignCoursesToProfessor'");
	}

	@Override
	public void generateReportCards() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'generateReportCards'");
	}
	
	

}
