package com.wibmo.dao;

import java.util.ArrayList;
import java.util.Map;

import com.wibmo.bean.ReportCard;

/**
 * 
 */
public interface ReportCardDAO {

	public void save(ReportCard reportCard);

    public Map<Integer, ArrayList<ReportCard>> findAllByStudentId(Integer studentId);

	public boolean checkGradeDetails(ReportCard reportCard);

    public void updateByReportId(ReportCard reportCard);
}
