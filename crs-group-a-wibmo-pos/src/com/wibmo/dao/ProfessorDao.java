package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Student;

public interface ProfessorDao {
	public List<Student> getStudentList();
	public boolean authentication();
}
