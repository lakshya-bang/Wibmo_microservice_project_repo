package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.entity.Professor;

/**
 * 
 */
public interface ProfessorDAO {

	/**
	 * 
	 * @param professorIds
	 * @return
	 */
	public List<Professor> findAllByIdIn(Set<Integer> professorIds);

	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Boolean existsById(Integer professorId);

	/**
	 * 
	 * @param professor
	 * @return
	 */
	public void save(Professor professor);
}