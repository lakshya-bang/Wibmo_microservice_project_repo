/**
 * 
 */
package com.wibmo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.repository.StudentRepository;

/**
 * 
 */
@Component
public class StudentUtils {
	
	@Autowired
	StudentRepository studentRepository;
	
	public Boolean isStudentExistsById(Integer studentId) {
	
		return studentRepository
				.findByStudentId(studentId)
				.isPresent();
	}
	
	public Integer getCurrentSemesterByStudentId(Integer studentId) {
		return studentRepository
				.findByStudentId(studentId)
				.get()
				.getCurrentSemester();
	}
}
