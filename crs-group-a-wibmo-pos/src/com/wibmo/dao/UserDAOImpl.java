package com.wibmo.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.User;
import com.wibmo.utils.DBUtils;

public class UserDAOImpl implements UserDAO {

	@Override
	public List<User> findAllByIdIn(Set<Integer> ids) {
		
		List<User> users = new ArrayList<>();
		
		String sql = "SELECT * FROM user"
				+ "WHERE user_id IN(?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			// Ref: https://stackoverflow.com/questions/3107044/preparedstatement-with-list-of-parameters-in-a-in-clause
			PreparedStatement stmt = conn.prepareStatement(sql);
			Array array = stmt.getConnection().createArrayOf("int", new Object[] {ids});
			stmt.setArray(1, array);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				users.add(new User(
						rs.getString("name"),
						rs.getInt("user_id"),
						rs.getString("address"),
						rs.getString("email"),
						rs.getString("type"),
						rs.getInt("number")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
}
