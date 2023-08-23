/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class UserDAOImpl implements UserDAO{

	private static volatile UserDAOImpl instance = null;
	
	private UserDAOImpl() {}
	
	public static UserDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new UserDAOImpl();
            }
        }
        return instance;
    }
	
	@Override
	public List<User> findAllByRegistrationStatus(RegistrationStatus registrationStatus) {
		
		List<User> users = new ArrayList<>();
		
		String sql = "SELECT * FROM auth_creds "
				+ "WHERE reg_status = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, registrationStatus.toString());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				users.add(new User(
						rs.getInt("user_id"),
						rs.getString("user_email"),
						RegistrationStatus.valueOf(
								rs.getString("reg_status")),
						UserType.valueOf(
								rs.getString("user_type"))));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return users;
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
	public Boolean updateRegistrationStatusAsByIdIn(
			RegistrationStatus registrationStatus, 
			Set<Integer> userIds) {
		
		StringBuilder sql = new StringBuilder(
				"UPDATE auth_creds "
				+ "SET reg_status = ? "
				+ "WHERE user_id IN (");
		userIds.forEach(userId -> sql.append(userId).append(","));
		sql.replace(sql.length() - 1, sql.length(), ")");
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, registrationStatus.toString());
			
			if(userIds.size() == stmt.executeUpdate()) {
				return Boolean.TRUE;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean existsById(Integer userId) {
		
		String sql = "SELECT user_id FROM auth_creds "
				+ "WHERE user_id = ? "
				+ "LIMIT 1";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return Boolean.TRUE;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return Boolean.FALSE;
	}
	
}
