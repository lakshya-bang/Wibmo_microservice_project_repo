package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;

/**
 * 
 */
public interface ProfessorOperation {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Professor getProfessorById(Integer userId);
	
	/**
	 * 
	 * @param professorIds
	 * @return
	 */
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds);

}
