package com.wibmo.client;

import java.util.Scanner;

import com.wibmo.bean.User;
import com.wibmo.business.AuthenticationServiceImpl;
import com.wibmo.business.FakeAuthenticationService;
import com.wibmo.dao.AuthenticationDaoImpl;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		char ch = '\0';
		
		
		User user = new AuthenticationServiceImpl().login();
		System.out.println("Details - " + user.getUserName() + " " + user.getUserId());
	}
}
