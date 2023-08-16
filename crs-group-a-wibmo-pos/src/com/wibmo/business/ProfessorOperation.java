package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;

/**
 * 
 */
public interface ProfessorOperation {
	
	public static Professor fetchDetails(User user) {
		Professor professor = new Professor();
		professor.setProfessorId(user.getUserId());
		return professor;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Professor getProfessorById(Integer userId);
	
	/**
	 * 
	 * @param professorIds
	 * @return
	 */
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds);
	
	/**
	 * 
	 * @param professorId
	 * @return
	 */
	public Map<Integer, List<Student>> getCourseIdToEnrolledStudentsMapping(Integer professorId);
	
	public void uploadGrades();
}
