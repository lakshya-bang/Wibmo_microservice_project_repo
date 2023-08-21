package com.wibmo.business;

import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Professor;

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

	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Boolean isProfessorExistsById(Integer professorId);

	/**
	 * 
	 * @param professor
	 */
	public void add(Professor professor);

}
