/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Repository;

import com.wibmo.constant.SQLConstants;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class AuthenticationDAOImpl implements AuthenticationDAO {
	
	// Plug Logger in AuthenticationDAOImpl
	private static final Logger logger = LogManager.getLogger(AuthenticationDAOImpl.class);
	
	@Override
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
		} catch(SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
