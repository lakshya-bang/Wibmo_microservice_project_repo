/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Course;
import com.wibmo.enums.CourseType;

/**
 * 
 */
//@Repository
//public interface CourseRepository extends CrudRepository<Course, Integer>{
//
//	List<Course> findAllBySemester(Integer currentSemester);
//
//	Collection<Course> findAllByCourseIdIn(Set<Integer> courseIds);
//
//	List<Course> findAllByProfessorId(Integer professorId);
//
//	CourseType findCourseTypeByCourseId(Integer courseId);
//
//	Boolean existsByCourseId(Integer courseId);
//
//	Integer findProfessorIdByCourseId(Integer courseId);
//
//	void setProfessorIdAsWhereCourseIdIs(Integer professorId, Integer courseId);
//
//	Boolean deleteCourseById(Integer courseId);
//
//}
