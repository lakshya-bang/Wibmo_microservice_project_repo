/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;

/**
 * 
 */
@Repository
@Component
public interface ProfessorRepository extends CrudRepository<Professor, Integer>{

}
