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

	Optional<Professor> findByProfessorId(Integer professorId);
	
	List<Professor> findAll();
	
	List<Professor> findAllByProfessorIdIn(Collection<Integer> professorIds);
}