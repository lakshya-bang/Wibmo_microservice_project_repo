package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.entity.Professor;

/**
 * @author lakshya.bang
 */
public interface ProfessorService {
	
	/**
	 * Adds professor to DB.
	 * @param professor
	 */
	public void add(Professor professor);

}
