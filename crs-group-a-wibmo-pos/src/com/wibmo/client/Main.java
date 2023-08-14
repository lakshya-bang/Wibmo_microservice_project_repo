package com.wibmo.client;

import java.util.List;
import java.util.Scanner;

import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.business.FakeAuthenticationService;
import com.wibmo.dao.AuthenticationDaoImpl;
import com.wibmo.dao.StudentDAOImpl;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		char ch = '\0';
		
		StudentDAOImpl fakeStudent = new StudentDAOImpl();
		
		fakeStudent.registerCourse(1005L, List.of(101,102,103));
		fakeStudent.viewRegisteredCourses(1005L);
	}
}
