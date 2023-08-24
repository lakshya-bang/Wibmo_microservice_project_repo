/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class UserDAOImpl implements UserDAO{

//	private static volatile UserDAOImpl instance = null;
//	
//	private UserDAOImpl() {}
//	
//	public static UserDAOImpl getInstance() {
//        if (instance == null) {
//            synchronized (UserDAOImpl.class) { //It's a synchronized object that will thread safe.
//                instance = new UserDAOImpl();
//            }
//        }
//        return instance;
//    }
	
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

	@Override
	public void save(User user) {

		String sql = "INSERT INTO auth_creds("
					+ "user_password, "
					+ "user_type, "
					+ "reg_status, "
					+ "user_email) "
					+ "VALUES(?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getUserType().toString());
			stmt.setString(3, RegistrationStatus.PENDING.toString());
			stmt.setString(4, user.getUserEmail());
		
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	@Override
	public Integer findUserIdByEmail(String email) {
		
		String sql = "SELECT user_id FROM auth_creds "
				+ "WHERE user_email = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("user_id");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public User findUserByEmail(String email) {
		User user = null;
		String sql = "SELECT * FROM auth_creds "
				+ "WHERE user_email = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				user = new User(rs.getInt("user_id"), rs.getString("user_email"), 
				RegistrationStatus.valueOf(rs.getString("reg_status")), 
				UserType.valueOf(rs.getString("user_type"))
				);
				user.setPassword(rs.getString("user_password"));
			}
			return user;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return null;
	}


}
