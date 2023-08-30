package com.wibmo.service;

import java.util.Collection;
import java.util.List;
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
	 * 
	 * @return
	 */
	List<Professor> getAllProfessors();
	
	/**
	 * Using the professor Ids fetches the professor details and returns the data as a Map.
	 * @param professorIds
	 * @return Map<Integer,Professor>
	 */
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Collection<Integer> professorIds);

	/**
	 * Creates a new entry in the professor table.
	 * @param professor
	 */
	public void add(Professor professor);

	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Boolean isProfessorExistsById(Integer professorId);

}
