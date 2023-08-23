package com.wibmo.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAO;
import com.wibmo.dao.StudentDAOImpl;

@Service
@Component
public class StudentOperationImpl implements StudentOperation {
	@Autowired
	private StudentDAOImpl studentDAO;
	
	public StudentOperationImpl() {
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
