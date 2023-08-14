/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.bean.User;
import com.wibmo.constants.SQLConstants;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class AuthenticationDaoImpl implements AuthenticationDao{
	
	private static volatile AuthenticationDaoImpl instance = null;
	private AuthenticationDaoImpl() {
		
	}
	
	
	public static AuthenticationDaoImpl getInstance() {
        if (instance == null) {
            synchronized (AuthenticationDaoImpl.class) { //It's a synchronized object that will thread safe.
                instance = new AuthenticationDaoImpl();
            }
        }
        return instance;
    }
	
	public static boolean authenticate(String user_name, String password) {
		PreparedStatement stmt = null;
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		try {
		stmt = conn.prepareStatement(SQLConstants.AUTH_DETAILS + "'" + user_name + "'");
	    ResultSet rs = stmt.executeQuery(SQLConstants.AUTH_DETAILS + "'" + user_name + "'");
	   if(rs.next()) {
		   if(password.equals(rs.getString("password"))) {
			   return true;
		   }
	   }
	   else {
		   return false;
	   }
	   }catch(SQLException se) {
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }
		return false;
	}
	
	public static User getUserDetails(String user_name) {
		PreparedStatement stmt = null;
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		try {
		stmt = conn.prepareStatement(SQLConstants.USER_DETAILS + "'" + user_name + "'");
	    ResultSet rs = stmt.executeQuery(SQLConstants.USER_DETAILS + "'" + user_name + "'");
	   if(rs.next()) {
		   System.out.println(rs.getLong(1));
		   return new User(rs.getString("name"), (long) rs.getInt("user_id"), rs.getString("address"), rs.getString("email"), rs.getString("type"), (long) rs.getInt("number"));
	   }
	   else {
		   return null;
	   }
	   }catch(SQLException se) {
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }
		return null;
	}
	

}
