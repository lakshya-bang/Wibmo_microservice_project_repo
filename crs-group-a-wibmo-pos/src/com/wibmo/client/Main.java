package com.wibmo.client;

import java.util.Scanner;

import com.wibmo.bean.User;
import com.wibmo.business.FakeAuthenticationService;
import com.wibmo.dao.AuthenticationDaoImpl;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		char ch = '\0';
		
		
		System.out.println("Enter Username: ");
		String username = scanner.next();
		System.out.println("Enter Password: ");
		String password = scanner.next();
		
		Boolean isTrue = AuthenticationDaoImpl.authenticate(username,password);
		System.out.println(isTrue);
	}
}
