/**
 * 
 */
package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.wibmo.dto.UserLogInDTO;
import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.dto.UserResponseDTO;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.DepartmentCannotBeEmptyException;
import com.wibmo.exception.SemesterCannotBeEmptyException;
import com.wibmo.exception.UserNotAuthorizedForLogIn;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

/**
 * 
 * @author mourila.vatsav
 * 
 */
public interface UserService {
	
	/**
	 * Gets the accounts that require approval from Admin
	 * @return List<UserResponseDTO> 
	 * */
	public List<UserResponseDTO> getAccountsPendingForApproval();
	
	/**
	 * 
	 * @param userRegistrationDTO
	 * @throws UserWithEmailAlreadyExistsException
	 * @throws SemesterCannotBeEmptyException
	 * @throws DepartmentCannotBeEmptyException
	 */
	public void add(UserRegistrationDTO userRegistrationDTO)
		throws 
			UserWithEmailAlreadyExistsException, 
			SemesterCannotBeEmptyException, 
			DepartmentCannotBeEmptyException;
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public Integer getUserIdByEmail(String email);

	/**
	 * 
	 * @param email
	 * @return
	 */
	public UserResponseDTO getUserByEmail(String email);
	
	/**
	 * 
	 * @param approved
	 * @param userIds
	 * @throws UserNotFoundException 
	 */
	public Boolean updateAccountRegistrationStatusToByUserIds(
			RegistrationStatus registrationStatus, 
			Collection<Integer> userIds) throws UserNotFoundException;

	/**
	 * 
	 * @param approved
	 */
	public Boolean updateAllPendingAccountRegistrationsTo(
			RegistrationStatus registrationStatus);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Boolean isUserExistsById(Integer userId);

	/**
	 * 
	 * @param userId
	 * @throws UserNotFoundException 
	 */
	void delete(Integer userId) throws UserNotFoundException;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserResponseDTO getUserById(Integer userId);

	/**
	 * 
	 * @return
	 */
	public List<UserResponseDTO> getAllUsers();

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public RegistrationStatus getRegistrationStatusById(Integer userId);
	
	/**
	 * 
	 * @param userEmail
	 * @param password
	 * @throws UserNotFoundException 
	 */
	public void logIn(UserLogInDTO userLoginDTO) 
			throws UserNotFoundException, UserNotAuthorizedForLogIn;
}
