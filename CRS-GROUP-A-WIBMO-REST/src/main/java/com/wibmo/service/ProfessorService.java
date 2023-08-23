/**
 * 
 */
package com.wibmo.business;

import java.util.Map;
import java.util.Set;

import org.jvnet.hk2.annotations.Service;

import com.wibmo.bean.Professor;

/**
 * 
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
	 * Checks if professorExists using professorId
	 * @param professorId
	 * @return
	 */
	public Boolean isProfessorExistsById(Integer professorId);

	/**
	 * Creates a new entry in the professor table.
	 * @param professor
	 */
	public void add(Professor professor);
}
