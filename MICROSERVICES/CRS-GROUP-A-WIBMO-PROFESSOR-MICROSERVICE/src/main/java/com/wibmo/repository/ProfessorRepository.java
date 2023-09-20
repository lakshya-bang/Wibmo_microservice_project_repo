/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Professor;

/**
 * 
 */
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer>{
	/**
	 * Fetches professor by professorId
	 * @param professorId
	 * @return Optional<Professor>
	 */
	Optional<Professor> findByProfessorId(Integer professorId);
	
	
	/**
	 * Fetches list of Professors.
	 * @return List<Professor>
	 */
	List<Professor> findAll();
	
	/**
	 * Fetches List of professors with ids in professorIds.
	 * @param professorIds
	 * @return List<Professor>
	 */
	List<Professor> findAllByProfessorIdIn(Collection<Integer> professorIds);

	/**
	 * Checks if professor with professorId exists.
	 * @param professorId
	 * @return Boolean
	 */
	Boolean existsByProfessorId(Integer professorId);

	@Query("Select professor FROM Professor professor WHERE professor.professorEmail=:userName")
	Professor findByProfessorUserName(String userName);
	
}