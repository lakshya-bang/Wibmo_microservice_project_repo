package com.wibmo.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.entity.Student;

/**
 * Utility class on Student
 */
public class StudentUtils {

	public static Map<Integer, Student> getStudentIdToStudentMap(
			Collection<Student> students) {
		return students
				.stream()
				.collect(Collectors.toMap(
						Student::getStudentId,
						Function.identity()));
	}
}
