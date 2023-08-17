/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.bean.User;
import com.wibmo.constant.SQLConstants;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class AuthenticationDAOImpl implements AuthenticationDAO {
	
	private static volatile AuthenticationDAOImpl instance = null;
	
	private AuthenticationDAOImpl() {}
	
	public static AuthenticationDAOImpl getInstance() {
        if (instance == null) {
            synchronized (AuthenticationDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new AuthenticationDAOImpl();
            }
        }
        return instance;
    }
	
	public static boolean authenticate(Integer userId, String password) {
		
		PreparedStatement stmt = null;
		Connection conn = DBUtils.getConnection();
		
		try {
		stmt = conn.prepareStatement(SQLConstants.AUTH_DETAILS);
		stmt.setInt(1, userId);
		
	    ResultSet rs = stmt.executeQuery();
	    
	   if(rs.next()) {
		   if(password.equals(rs.getString("user_password"))) {
			   return true;
		   }
	   }
	   }catch(SQLException se) {
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }
		return false;
	}
	
	public static User getUserDetails(Integer userId) {
		PreparedStatement stmt = null;
		Connection conn = DBUtils.getConnection();
		try {
		stmt = conn.prepareStatement(SQLConstants.USER_DETAILS);
	    stmt.setInt(1, userId);
		
		ResultSet rs = stmt.executeQuery();
	    
	    
	   if(rs.next()) {
		   return new User(
				   rs.getInt("user_id"),
				   RegistrationStatus.valueOf(rs.getString("reg_status")),
				   UserType.valueOf(rs.getString("user_type")));
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
