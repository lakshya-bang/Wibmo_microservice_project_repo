/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.constant.SQLConstants;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.utils.DBUtils;


/**
 * 
 */
@Repository
public class AuthenticationDAOImpl implements AuthenticationDAO {
	
	// Plug Logger in AuthenticationDAOImpl
	private static Logger logger = Logger.getLogger(AuthenticationDAOImpl.class);
	
	// NOTE:-
	// 1. INFO  ----> To get the information (logger.info)
	// 2. DEBUG ----> Replace sysout debug statements (logger.debug)
	// 3. ERROR ----> Catch Handled Exceptions (logger.error)
	
//	private static volatile AuthenticationDAOImpl instance = null;
//	
//	private AuthenticationDAOImpl() {}
//	
//	public static AuthenticationDAOImpl getInstance() {
//        if (instance == null) {
//            synchronized (AuthenticationDAOImpl.class) { //It's a synchronized object that will thread safe.
//                instance = new AuthenticationDAOImpl();
//            }
//        }
//        return instance;
//    }
	
	public boolean authenticate(String userEmail, String password) {
		
		PreparedStatement stmt = null;
		Connection conn = DBUtils.getConnection();
		
		try {
		stmt = conn.prepareStatement(SQLConstants.AUTH_DETAILS);
		stmt.setString(1, userEmail);
		
	    ResultSet rs = stmt.executeQuery();
	    
	   if(rs.next()) {
		   if(password.equals(rs.getString("user_password"))) {
			   return true;
		   }
	   }
	   }catch(SQLException se) {
		   logger.error("SQL Exception: " + se.getMessage());
//	      se.printStackTrace();
	   }catch(Exception e){
		   logger.error(e.getMessage());
//	      e.printStackTrace();
	   }
		return false;
	}
	
	public User getUserDetails(String userEmail) {
		PreparedStatement stmt = null;
		Connection conn = DBUtils.getConnection();
		try {
		stmt = conn.prepareStatement(SQLConstants.USER_DETAILS);
	    stmt.setString(1, userEmail);
		
		ResultSet rs = stmt.executeQuery();
		
	   if(rs.next()) {
		   return new User(
				   rs.getInt("user_id"),
				   rs.getString("user_email"),
				   RegistrationStatus.valueOf(rs.getString("reg_status")),
				   UserType.valueOf(rs.getString("user_type")));
	   }
	   else {
		   return null;
	   }
	   }catch(SQLException se) {
		   logger.error(se.getMessage());
	   }catch(Exception e){
		   logger.error(e.getMessage());
	   }
		return null;
	}
	

}
