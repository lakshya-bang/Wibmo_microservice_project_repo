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
	
	/**
	 * Fetches student with studentId.
	 * @param studentId
	 * @return Optional<Student>
	 */
	Optional<Student> findByStudentId(Integer studentId);
	
	
	/**
	 * Fetches list of all the Students.
	 * @return List<Student>
	 */
	List<Student> findAll();
	
	
	/**
	 * Fetches list of Students with ids in studentIds.
	 * @param studentIds
	 * @return List<Student>
	 */
	List<Student> findAllByStudentIdIn(Collection<Integer> studentIds);
	
	
	/**
	 * Checks if the student with studentId exists.
	 * @param studentId
	 * @return Boolean
	 */
	Boolean existsByStudentId(Integer studentId);
	
}
