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

public interface CourseRepository extends CrudRepository<Course, Integer>{



}
