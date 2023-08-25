package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Admin;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class AdminDAOImpl implements AdminDAO {
	private static Logger logger = Logger.getLogger(AdminDAOImpl.class);
	@Override
	public Admin findById(Integer adminId) {
		
		Admin admin = null;
		
		String sql = "SELECT * FROM admin "
				+ "WHERE admin_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, adminId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				admin = new Admin(
						rs.getInt("admin_id"),
						rs.getString("admin_name"),
						rs.getString("admin_email"));
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
//			e.printStackTrace();
		}
		
		return admin;
	}
	
	@Override
	public void save(Admin admin) {
		
		String sql = "INSERT INTO admin "
				+ "VALUES(?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, admin.getAdminId());
			stmt.setString(2, admin.getAdminName());
			stmt.setString(3, admin.getAdminEmail());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
//			e.printStackTrace();
		}
		
	}

}
