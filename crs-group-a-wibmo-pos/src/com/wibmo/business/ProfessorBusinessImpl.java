/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.dao.ProfessorDAOImpl;

/**
 * 
 */
public class ProfessorBusinessImpl implements ProfessorBusiness{

	@Override
	public void viewEnrolledStudents(Professor professor) {
		ProfessorDAOImpl professorDao = ProfessorDAOImpl.getInstance();
		Map<Integer,ArrayList<Integer>> studentMap = professorDao.getStudentList(professor.getCoursesTaught());
		for(Map.Entry<Integer, ArrayList<Integer>> entry : studentMap.entrySet()) {
			System.out.println("For Course with ID - " + entry.getKey());
			System.out.println("List of Students - " + entry.getValue());
			System.out.println("========================================");
		}
	}
	
}
