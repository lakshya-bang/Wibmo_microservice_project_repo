package com.wibmo.bean;

import java.util.Map;
import java.util.Set;

public class Student {
	
	private Long id;
	private Map<Integer, Set<Long>> semesterToRegisteredCourseIdsMapping;
	
	public Long getId() {
		return id;
	}
	
}
