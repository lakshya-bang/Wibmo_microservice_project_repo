/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Course;
import com.wibmo.enums.CourseType;

/**
 * 
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Integer>{

	Optional<Course> findByCourseId(Integer courseId);
	
	List<Course> findAll();
	
	List<Course> findAllBySemester(Integer currentSemester);

	List<Course> findAllByCourseIdIn(Collection<Integer> courseIds);

	List<Course> findAllByProfessorId(Integer professorId);

	Boolean existsByCourseId(Integer courseId);

}
