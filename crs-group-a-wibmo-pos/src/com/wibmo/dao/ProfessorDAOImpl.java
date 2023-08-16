package com.wibmo.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.Professor;
import com.wibmo.bean.User;
import com.wibmo.utils.DBUtils;

public class ProfessorDAOImpl implements ProfessorDAO {

	private static volatile ProfessorDAOImpl instance = null;
	
	private ProfessorDAOImpl() {
		
	}
	
	public static ProfessorDAOImpl getInstance() {
        if (instance == null) {
            synchronized (ProfessorDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new ProfessorDAOImpl();
            }
        }
        return instance;
    }

	@Override
	public List<Professor> findAllByIdIn(Set<Integer> ids) {
		List<Professor> professors = new ArrayList<>();
		
		String sql = "SELECT * FROM professor "
				+ "WHERE professor_id IN(?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			Array array = stmt.getConnection().createArrayOf("int", new Object[] {ids});
			stmt.setArray(1, array);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				professors.add(new Professor(
						rs.getInt("professor_id"),
						rs.getString("department")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}
	
}
