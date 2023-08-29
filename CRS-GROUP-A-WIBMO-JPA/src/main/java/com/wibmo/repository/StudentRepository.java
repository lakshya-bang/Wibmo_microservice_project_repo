/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Student;

/**
 * 
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>{
	
	Optional<Student> findByStudentId(Integer studentId);
	
	List<Student> findAll();
	
	List<Student> findAllByStudentIdIn(Collection<Integer> studentIds);
	
}
