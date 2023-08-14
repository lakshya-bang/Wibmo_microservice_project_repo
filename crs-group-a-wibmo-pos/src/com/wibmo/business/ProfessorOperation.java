package com.wibmo.business;

import java.util.List;
import java.util.Map;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;

/**
 * 
 */
public interface ProfessorOperation {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Professor getProfessorById(Long userId);
	
	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Map<Long, List<Student>> getCourseIdToEnrolledStudentsMapping(Long professorId);
}
