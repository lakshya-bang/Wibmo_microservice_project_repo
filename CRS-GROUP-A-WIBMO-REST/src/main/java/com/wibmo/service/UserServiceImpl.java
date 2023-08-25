/**
 * @author mourila.vatsav
 */
package com.wibmo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.UserDAOImpl;
import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.DepartmentCannotBeEmptyException;
import com.wibmo.exception.SemesterCannotBeEmptyException;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

/**
 * 
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@Autowired
	private ProfessorServiceImpl professorService;
	
	@Autowired
	public AdminServiceImpl adminService;
	
	@Autowired
	private UserDAOImpl userDAO;

	@Override
	public List<User> viewAccountsPendingForApproval() {
			// TODO Auto-generated method stub
		List<User> pendingAccounts = userDAO.findAllByRegistrationStatus(RegistrationStatus.PENDING);
			return pendingAccounts;
		}
	
	@Override //Will go in Admin route
	public boolean approveLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userDAO.update("APPROVED", userId);
		return flag;
	}

	@Override //Will go in Admin route
	public boolean rejectLoginById(int userId) {
		// TODO Auto-generated method stub
		boolean flag = userDAO.update("REJECTED", userId);
		return flag;
		
	}

	@Override
	public void add(UserRegistrationDTO userRegistrationDTO) 
		throws 
			UserWithEmailAlreadyExistsException, 
			SemesterCannotBeEmptyException, 
			DepartmentCannotBeEmptyException {
		
		// TODO: Add Name Validation using Regex
		
		// TODO: Add Email Validation using Regex
		
		// TODO: Add Password Validation using Regex
		
		if(isEmailAlreadyInUse(
				userRegistrationDTO.getUserEmail())) {
			throw new UserWithEmailAlreadyExistsException(
					userRegistrationDTO.getUserEmail());
		}
		
		switch(userRegistrationDTO.getUserType()) {
		case STUDENT:
			if(userRegistrationDTO.getSemester() == null) {
				throw new SemesterCannotBeEmptyException();
			}
			break;
		case PROFESSOR:
			if(userRegistrationDTO.getDepartment() == null) {
				throw new DepartmentCannotBeEmptyException();
			}
			break;
		default:
		}
		
		User user = new User();
		user.setUserEmail(userRegistrationDTO.getUserEmail());
		user.setPassword(userRegistrationDTO.getPassword());
		user.setUserType(userRegistrationDTO.getUserType());
		user.setRegistrationStatus(RegistrationStatus.PENDING);
		
		userDAO.save(user);
		
		Integer userId = userDAO.findUserIdByEmail(user.getUserEmail());
		
		switch(user.getUserType()) {
		case ADMIN:
			adminService.add(new Admin(
					userId,
					user.getUserEmail(),
					userRegistrationDTO.getUserName()));
			break;
		case PROFESSOR:
			professorService.add(new Professor(
					userId,
					user.getUserEmail(),
					userRegistrationDTO.getUserName(),
					userRegistrationDTO.getDepartment()
					));
			break;
		case STUDENT:
			studentService.add(new Student(
					userId,
					user.getUserEmail(),
					userRegistrationDTO.getUserName(),
					userRegistrationDTO.getSemester()));
		}
	}
	
	@Override
	public Integer getUserIdByEmail(String email) {
		return userDAO.findUserIdByEmail(email);
	}
	
	@Override
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus,
			Set<Integer> userIds) {
		
		if(null == userIds || userIds.isEmpty()) {
			return Boolean.FALSE;
		}
		
		return userDAO.updateRegistrationStatusAsByIdIn(
								registrationStatus, userIds);
		
	}
	
	@Override
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus) {
		
		return userDAO.updateRegistrationStatusAsByIdIn(
				registrationStatus,
				userDAO
					.findAllByRegistrationStatus(
						RegistrationStatus.PENDING)
					.stream()
					.map(user -> user.getUserId())
					.collect(Collectors.toSet()));
	}
	
	@Override
	public Boolean isUserExistsById(Integer userId) {
		return userDAO.existsById(userId);
	}
	

	/*************************** Utility Methods ***************************/
	
	private boolean isEmailAlreadyInUse(String email) {
		return null != userDAO.findUserIdByEmail(email);
	}
}
