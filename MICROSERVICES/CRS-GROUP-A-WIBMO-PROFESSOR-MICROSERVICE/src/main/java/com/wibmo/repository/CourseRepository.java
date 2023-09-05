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
	/**
	 * Fetches course by courseId
	 * @param courseId
	 * @return Optional<Course>
	 */
	Optional<Course> findByCourseId(Integer courseId);
	
	/**
	 * Fetches list of all the courses.
	 * @return List<Course>
	 */
	List<Course> findAll();
	
	
	/**
	 * Fetches list of courses in the currentSemester.
	 * @param currentSemester
	 * @return List<Course>
	 */
	List<Course> findAllBySemester(Integer currentSemester);

	/**
	 * Fetches all the courses with ids in courseIds.
	 * @param courseIds
	 * @return List<Course>
	 */
	
	List<Course> findAllByCourseIdIn(Collection<Integer> courseIds);

	/**
	 * Fetches all the courses by professorId.
	 * @param professorId
	 * @return List<Course>
	 */
	List<Course> findAllByProfessorId(Integer professorId);

	/**
	 * Checks if the course with courseId exists
	 * @param courseId
	 * @return
	 */
	Boolean existsByCourseId(Integer courseId);

}
