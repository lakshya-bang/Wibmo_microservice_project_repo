package com.wibmo.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.entity.Course;

/**
 * Utility class on Course
 */
public class CourseUtils {
	
	public static Collection<Integer> getCourseIds(Collection<Course> courses) {
		return courses
			.stream()
			.map(Course::getCourseId)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
	}
	
	public static Collection<Integer> getProfessorIds(Collection<Course> courses) {
		return courses
				.stream()
				.map(Course::getProfessorId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}
	
	public static Map<Integer, Course> getCourseIdToCourseMap(Collection<Course> courses) {
		return courses
				.stream()
				.collect(Collectors.toMap(
					Course::getCourseId,
					Function.identity()));
	}
	
}
