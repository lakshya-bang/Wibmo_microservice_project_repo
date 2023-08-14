package com.wibmo.dao;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.CourseCatalogue;

/**
 * 
 */
public interface CourseCatalogueDAO {

	/**
	 * 
	 * @return
	 */
	public List<CourseCatalogue> findAll();
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public List<CourseCatalogue> findAllByCourseIdIn(Set<Long> courseIds);
}
