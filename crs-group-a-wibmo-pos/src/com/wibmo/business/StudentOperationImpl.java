package com.wibmo.business;

import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAO;

public class StudentOperationImpl implements StudentOperation {

	private final StudentDAO studentDAO;
	
	public StudentOperationImpl() {
		studentDAO = StudentDAOImpl.getInstance();
	}
	
	@Override
	public Student getStudentById(Integer studentId) {
		return studentDAO.findById(studentId);
	}

	@Override
	public void add(Student student) {
		
		// TODO
//		if(!userOperation.isUserExistsById(student.getStudentId())) {
//			
//		}
		
		studentDAO.save(student);
		System.out.println("Account Registration sent to Admin for Approval");
	}
	
}
