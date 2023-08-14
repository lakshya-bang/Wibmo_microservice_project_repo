package com.wibmo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;

public interface ProfessorDAO {
	public List<Integer> fetchCoursesTaught(long id,int year, int semester);
	public Map<Integer, ArrayList<Integer>> getStudentList(List<Integer> courses);
	public Boolean updateGrades(Map<Integer, Character> gradeSheet, Integer year, Integer semester, Integer reportId, Integer studentId);
}
