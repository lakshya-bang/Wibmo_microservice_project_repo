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
	
	
	/**
	 * Checks if the student with studentId exists.
	 * @param studentId
	 * @return
	 */
	public Boolean isStudentExistsById(Integer studentId) {
	
		return studentRepository
				.findByStudentId(studentId)
				.isPresent();
	}
	/**
	 * Fetches the current semester by the studentId.
	 * @param studentId
	 * @return
	 */
	public Integer getCurrentSemesterByStudentId(Integer studentId) {
		return studentRepository
				.findByStudentId(studentId)
				.get()
				.getCurrentSemester();
	}
}
