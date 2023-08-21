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
	
	private ProfessorDAOImpl() {}
	
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
						rs.getString("professor_email"),
						rs.getString("professor_name"),
						rs.getString("department")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}

	@Override
	public Boolean existsById(Integer professorId) {
		
		String sql = "SELECT professor_id FROM professor "
				+ "WHERE professor_id = ? "
				+ "LIMIT 1";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, professorId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void save(Professor professor) {
		
		String sql = "INSERT INTO professor "
				+ "VALUES(?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, professor.getProfessorId());
			stmt.setString(2, professor.getProfessorName());
			// TODO: Department should be an Enum
			stmt.setString(3, professor.getDepartment());
			stmt.setString(4, professor.getProfessorEmail());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	}
	
}
