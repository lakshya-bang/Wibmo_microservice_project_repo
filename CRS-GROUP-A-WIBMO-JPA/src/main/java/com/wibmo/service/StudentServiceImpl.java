package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.entity.Student;
import com.wibmo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;
	
	@Override
	public Student getStudentById(Integer studentId) {
		return studentRepository.findById(studentId);
	}

	@Override
	public void add(Student student) {
		
		if(null == student || null != student.getStudentId()) {
			return;
		}
		
		studentRepository.save(student);
		
		System.out.println("Account Registration sent to Admin for Approval");
	}
	
}
