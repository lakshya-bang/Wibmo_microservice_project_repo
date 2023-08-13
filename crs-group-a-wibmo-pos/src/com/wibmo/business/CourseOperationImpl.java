package com.wibmo.business;

import java.util.Map;
import java.util.Set;

public class CourseOperationImpl implements CourseOperation {

	@Override
	public Map<Long, Integer> getCourseIdToVacantSeatsMapping(Set<Long> courseId) {
		// TODO get pending seats from CourseDAO
		return null;
	}

	@Override
	public void reduceSeatsBy1(Set<Long> courseIds) {
		// TODO Auto-generated method stub
		
	}

}
