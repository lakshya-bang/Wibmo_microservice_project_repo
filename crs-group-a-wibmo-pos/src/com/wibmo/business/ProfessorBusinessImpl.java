/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Professor;
import com.wibmo.dao.CourseRegistrationDAO;
import com.wibmo.dao.CourseRegistrationDAOImpl;
import com.wibmo.dao.ReportCardDAO;
import com.wibmo.dao.ReportCardDAOImpl;

/**
 * 
 */
public class ProfessorBusinessImpl implements ProfessorBusiness{

	@Override
	public void viewEnrolledStudents(Professor professor) {
		CourseRegistrationDAO courseRegistrationDao = new CourseRegistrationDAOImpl();
		Map<Integer, ArrayList<Integer>> studentIDLists =courseRegistrationDao.getStudentsByCourseId(professor);;
		for(Map.Entry<Integer,ArrayList<Integer>> entry : studentIDLists.entrySet() ){
			System.out.println("For courseID - " + entry.getKey() + "\nThe registered students are - ");
			System.out.println(entry.getValue());
			System.out.println("----------------------------------------");
		}
		System.out.println("========================================================");
	}

	
}
