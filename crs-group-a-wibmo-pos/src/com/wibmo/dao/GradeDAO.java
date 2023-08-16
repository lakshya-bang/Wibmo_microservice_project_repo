package com.wibmo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.wibmo.bean.Grade;

/**
 * 
 */
public interface GradeDAO {

	/**
	 * 
	 * @param grade
	 */
	public void save(Grade grade);

    public Map<Integer, ArrayList<Grade>> findAllByStudentId(Integer studentId);

	public boolean checkGradeDetails(Grade grade);

    public void updateByGradeId(Grade grade);
}
