package com.wibmo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.wibmo.bean.ReportCard;

/**
 * 
 */
public interface ReportCardDAO {

	public void save(ReportCard grade);

    public Map<Integer, ArrayList<ReportCard>> findAllByStudentId(Integer studentId);

	public boolean checkGradeDetails(ReportCard grade);

    public void updateByGradeId(ReportCard grade);
}
