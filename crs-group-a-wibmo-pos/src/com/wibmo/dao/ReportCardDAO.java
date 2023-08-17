package com.wibmo.dao;

import java.util.ArrayList;
import java.util.Map;
import com.wibmo.bean.ReportCard;

/**
 * 
 */
public interface ReportCardDAO {

	public void save(ReportCard grade);

    public Map<Integer, ArrayList<ReportCard>> findAllByStudentId(Integer studentId);

	public boolean checkGradeDetails(ReportCard grade);

    public void updateByGradeDetails(ReportCard grade);
}
