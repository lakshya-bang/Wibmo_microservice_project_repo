/**
 * 
 */
package com.wibmo.business;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.bean.Professor;
import com.wibmo.dao.ProfessorDAO;
//import com.wibmo.dao.ProfessorDAOImpl;
import com.wibmo.rest.ProfessorRESTController;

/**
 * 
 */
@Service
@Component
public class ProfessorServiceImpl implements ProfessorService{
	private static final Logger logger = LogManager.getLogger(ProfessorRESTController.class);

	@Autowired
	private ProfessorDAO professorDAO;
	
	
//	public ProfessorServiceImpl() {
//		professorDAO = ProfessorDAOImpl.getInstance();
//	}
	
	/**
	 * @param professorId (Integer)
	 * @return Professor
	 */
	@Override
	public Professor getProfessorById(Integer professorId) {
		return professorDAO
				.findAllByIdIn(Set.of(professorId))
				.get(0);
	}
	
	/**
	 * @param professorIds (Integer Set)
	 * @return Map<Integer,Professor>
	 */

	@Override
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds) {
		return professorDAO
				.findAllByIdIn(professorIds)
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId, 
						Function.identity()));
	}

	/**
	 * @param professorId (Integer)
	 * @return Boolean
	 */
	
	@Override
	public Boolean isProfessorExistsById(Integer professorId) {
		return professorDAO
				.existsById(professorId);
	}

	/**
	 * @param Professor
	 */
	
	@Override
	public void add(Professor professor) {
		
		// TODO
//		if(!userOperation.isUserExistsById(professor.getProfessorId())) {
//			
//		}
		
		professorDAO.save(professor);
		
		logger.info("Account Registration sent to Admin for Approval.");
	}
}
