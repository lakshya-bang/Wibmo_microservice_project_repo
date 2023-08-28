/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;

/**
 * 
 */
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer>{

	List<Professor> findAllByProfessorIdIn(Collection<Integer> professorIds);
}