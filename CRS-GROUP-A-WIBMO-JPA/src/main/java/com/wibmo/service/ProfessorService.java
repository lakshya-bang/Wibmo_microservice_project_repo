package com.wibmo.service;

import java.util.Map;
import java.util.Set;

import com.wibmo.entity.Professor;

/**
 * @author lakshya.bang
 */
public interface ProfessorService {
	
	/**
	 * Fetches the data of the professor saved in the DB by utilising Id as the index.
	 * @param userId
	 * @return	Professor
	 */
	public Professor getProfessorById(Integer userId);
	
	/**
	 * Using the professor Ids fetches the professor details and returns the data as a Map.
	 * @param professorIds
	 * @return Map<Integer,Professor>
	 */
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds);

	/**
	 * Creates a new entry in the professor table.
	 * @param professor
	 */
	public void add(Professor professor);

}
