package com.wibmo.business;

import java.util.Map;
import java.util.Set;

public interface CourseOperation {

	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public Map<Long, Integer> getCourseIdToVacantSeatsMapping(Set<Long> courseIds);
	
	/**
	 * Reduces the available seat count for the given course ids
	 * each by 1.
	 * 
	 * <b>NOTE</b>: This method should be synchronized
	 * 
	 * @param courseIds
	 */
	public void reduceSeatsBy1(Set<Long> courseIds);
}
