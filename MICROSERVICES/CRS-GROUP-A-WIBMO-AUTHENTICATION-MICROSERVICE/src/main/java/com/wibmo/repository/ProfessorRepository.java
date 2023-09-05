/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Professor;

/**
 * 
 */
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer>{
	/**
	 * Fetches professor with professorId.
	 * @param professorId
	 * @return Optional<Professor>
	 */
	Optional<Professor> findByProfessorId(Integer professorId);
	
	/**
	 * Finds all the professors in the DB.
	 * @return List<Professor>
	 */
	List<Professor> findAll();
	
	/**
	 * Fetches the list of professor with Ids in professorIds.
	 * @param professorIds
	 * @return List<Professor>
	 */
	
	List<Professor> findAllByProfessorIdIn(Collection<Integer> professorIds);

	/**
	 * Checks if the professor with professorId exists.
	 * @param professorId
	 * @return
	 */
	Boolean existsByProfessorId(Integer professorId);
	
}