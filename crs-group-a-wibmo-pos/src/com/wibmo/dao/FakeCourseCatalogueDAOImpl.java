package com.wibmo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.wibmo.bean.CourseCatalogue;

public class FakeCourseCatalogueDAOImpl implements CourseCatalogueDAO {

	Map<Long, CourseCatalogue> courseIdToCourseCatalogueMapping = new HashMap<>(Map.of(
			101L, new CourseCatalogue(101L, 2001L, 5),
			102L, new CourseCatalogue(102L, 2002L, 5),
			103L, new CourseCatalogue(103L, 2001L, 7),
			104L, new CourseCatalogue(104L, 2003L, 3),
			105L, new CourseCatalogue(105L, 2002L, 6),
			106L, new CourseCatalogue(106L, 2003L, 5),
			107L, new CourseCatalogue(107L, 2002L, 4),
			108L, new CourseCatalogue(108L, 2001L, 5)));
	
	@Override
	public List<CourseCatalogue> findAll() {
		return courseIdToCourseCatalogueMapping
			.entrySet()
			.stream()
			.map(entry -> entry.getValue())
			.collect(Collectors.toList());
	}

	@Override
	public List<CourseCatalogue> findAllByCourseIdIn(Set<Long> courseIds) {
		return courseIds
			.stream()
			.map(courseId -> courseIdToCourseCatalogueMapping.get(courseId))
			.filter(courseCatalogue -> courseCatalogue != null)
			.collect(Collectors.toList());
	}
	
}
