/**
 * 
 */
package com.wibmo.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.entity.Course;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.repository.CourseRepository;

/**
 * 
 */
@Component
public class CourseUtils {
	
	@Autowired
	CourseRepository courseRepository;
	
	public Boolean isCourseExistsInCatalog(Integer courseId) {
		return courseRepository
				.existsByCourseId(courseId);
	}
	
	public Map<Integer, Course> getCourseIdToCourseMap(Collection<Integer> courseIds) {
		return courseRepository
				.findAllByCourseIdIn(courseIds)
				.stream()
				.collect(Collectors.toMap(
						Course::getCourseId,
						Function.identity()));
	}
	
	public CourseType getCourseTypeByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException {
		
		Optional<Course> courseOptional = courseRepository.findByCourseId(courseId);
		
		if(courseOptional.isEmpty()) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		return courseOptional.get().getCourseType();
	}

}
