/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.constant.SQLConstants;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class UserDAOimpl implements UserDAO{

	@Override
	public List<Integer> view() {
		// TODO Auto-generated method stub
		CourseRegistration courseRegistration = null;
		List<Integer> resultOfPendingusers =null;
		
		String sql = "SELECT user_id FROM auth_creds "
				+ "WHERE reg_status = 'PENDING'";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			
			ResultSet rs = stmt.executeQuery();
			
			
			while(rs.next()) {
				resultOfPendingusers.add(rs.getInt("user_id"));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return resultOfPendingusers;
	}

	@Override
	public Boolean update(String status , int userId) {
		// TODO Auto-generated method stub
		String sql = "UPDATE auth_creds SET reg_status =? where userId =?";
		Connection conn = DBUtils.getConnection();
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setInt(2, userId);

			stmt.executeUpdate();
			return true;
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
			return false;
		}
		
	}
	

}
