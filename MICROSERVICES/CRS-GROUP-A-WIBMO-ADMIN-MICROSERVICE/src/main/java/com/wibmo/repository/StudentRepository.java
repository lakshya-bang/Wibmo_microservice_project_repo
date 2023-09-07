package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
	
	Boolean existsByStudentId(Integer studentId);
	
	@Query(value="SELECT student.user_id "
			+ "FROM user_v2.student "
			+ "as student "
			+ "INNER JOIN user_v2.registered_courses "
			+ "as registered_courses "
			+ "ON student.student_id = registered_courses.student_id "
			+ "WHERE registered_courses.registration_id = :registrationId;", nativeQuery=true)
	Integer findUserIdByStudentId(Integer registrationId);
	
}
