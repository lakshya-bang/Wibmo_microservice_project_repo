package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.Professor;
import com.wibmo.constant.SQLConstants;
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
		
		StringBuilder sql = new StringBuilder(
				SQLConstants.FIND_PROFESSOR_BY_IDS);
		
		sql.append("(");
		ids.forEach(id -> sql.append(id).append(","));
		int idx = sql.lastIndexOf(",");
		sql.replace(idx, idx + 1, ")");
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				professors.add(new Professor(
						rs.getInt("professor_id"),
						rs.getString("professor_name"),
						rs.getString("department")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}
	
}
