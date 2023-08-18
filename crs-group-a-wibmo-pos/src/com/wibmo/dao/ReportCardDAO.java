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

    // TODO: Remove to checkReportCardDetails()
    // TODO: Redundant Functionality ----> move to Business instead
	public boolean checkGradeDetails(ReportCard reportCard);

    // TODO: Rename to updateByReportCard()
    public void updateByGradeDetails(ReportCard reportCard);
}
