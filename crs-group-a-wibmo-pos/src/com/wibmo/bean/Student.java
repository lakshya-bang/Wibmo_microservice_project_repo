package com.wibmo.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student {
	
	private Long id;
	private Map<Integer, Set<Long>> semesterToRegisteredCourseIdsMapping;
	private Map<Integer, List<Grade>> semesterToRegisteredCourse;
	
	public Long getId() {
		return id;
	}
	
	
}
