package com.wibmo.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.entity.CourseRegistration;

public class CourseRegistrationUtils {

	public static Collection<Integer> getCourseIds(CourseRegistration courseRegistration) {
		Set<Integer> courseIds = new HashSet<>();
		if(null != courseRegistration.getPrimaryCourse1Id()) {
			courseIds.add(courseRegistration.getPrimaryCourse1Id());
		}
		if(null != courseRegistration.getPrimaryCourse2Id()) {
			courseIds.add(courseRegistration.getPrimaryCourse2Id());
		}
		if(null != courseRegistration.getPrimaryCourse3Id()) {
			courseIds.add(courseRegistration.getPrimaryCourse3Id());
		}
		if(null != courseRegistration.getPrimaryCourse4Id()) {
			courseIds.add(courseRegistration.getAlternativeCourse1Id());
		}
		if(null != courseRegistration.getAlternativeCourse1Id()) {
			courseIds.add(courseRegistration.getAlternativeCourse1Id());
		}
		if(null != courseRegistration.getAlternativeCourse2Id()) {
			courseIds.add(courseRegistration.getAlternativeCourse2Id());
		}
		return courseIds;
	}
	
	public static Collection<Integer> getCourseIds(
			Collection<CourseRegistration> courseRegistrations) {
		Set<Integer> courseIds = new HashSet<>();
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse1Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse2Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse3Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse4Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getAlternativeCourse1Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getAlternativeCourse2Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		return courseIds;
	}
}
