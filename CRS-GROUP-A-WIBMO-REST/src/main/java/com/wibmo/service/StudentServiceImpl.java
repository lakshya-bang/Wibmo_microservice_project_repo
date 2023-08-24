package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.entity.Student;
import com.wibmo.dao.StudentDAO;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDAOImpl studentDAO;
	
	@Override
	public Student getStudentById(Integer studentId) {
		return studentDAO.findById(studentId);
	}

	@Override
	public void add(Student student) {
		
		if(null == student || null != student.getStudentId()) {
			return;
		}
		
		studentDAO.save(student);
		
		System.out.println("Account Registration sent to Admin for Approval");
	}
	
}
