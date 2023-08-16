package com.wibmo.business;

import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAO;

public class StudentOperationImpl implements StudentOperation {

	StudentDAO studentDAO = new StudentDAOImpl();
	
	@Override
	public Student getStudentById(Integer studentId) {
		return studentDAO.findById(studentId);
	}
	
}
