package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;

public interface ProfessorDAO {
	
	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Professor findById(Long professorId);
}
