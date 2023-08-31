package com.wibmo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

/**
 * 
 */
public interface ReportCardDAO {

	public void save(ReportCard reportCard);
	
	public void update(ReportCard reportCard);

    public Map<Integer, ArrayList<ReportCard>> findAllByStudentId(Integer studentId);

	public ReportCard findByStudentAndCourseId(Student student, Integer courseId);

    // TODO: Remove to checkReportCardDetails()
    // TODO: Redundant Functionality ----> move to Business instead
//	public boolean checkGradeDetails(ReportCard reportCard);
}
