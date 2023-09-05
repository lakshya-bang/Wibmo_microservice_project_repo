/**
 * 
 */
package com.wibmo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.repository.AdminRepository;
import com.wibmo.repository.AuthenticationRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.service.AdminService;
import com.wibmo.service.ProfessorService;
import com.wibmo.service.StudentService;
/**
 * 
 */
@Component
public class UserRegistrationUtil {
	
	@Autowired
	AuthenticationRepository authenticationRepository;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	ProfessorService professorService;

	/**
	 * Saves the user details in the DB by checking the type of user.
	 * @param userRegistrationDto
	 * @return
	 * @throws UserWithEmailAlreadyExistsException
	 */
	public User saveRegDetailsUser(UserRegistrationDTO userRegistrationDto) throws UserWithEmailAlreadyExistsException{
		
		if(isEmailAlreadyInUse(userRegistrationDto.getUserEmail())) {
			throw new UserWithEmailAlreadyExistsException(userRegistrationDto.getUserEmail());
		}
		User newUser = new User();
		newUser.setUserEmail(userRegistrationDto.getUserEmail());
		newUser.setPassword(userRegistrationDto.getPassword());
		newUser.setUserType(userRegistrationDto.getUserType());
		authenticationRepository.save(newUser);
		System.out.println();
		
		switch(userRegistrationDto.getUserType()) {
		case ADMIN:
		{
			Admin admin = new Admin(null,newUser.getUserId(),userRegistrationDto.getUserEmail(),userRegistrationDto.getName());
			adminService.add(admin);
		}
			
			break;
		case PROFESSOR:
		{
			Professor professor = new Professor(null,newUser.getUserId(),userRegistrationDto.getUserEmail(),userRegistrationDto.getName(),userRegistrationDto.getDepartment());
			professorService.add(professor);
		}
			break;
		case STUDENT:
		{
			Student student = new Student(null,newUser.getUserId(),userRegistrationDto.getUserEmail(),userRegistrationDto.getName(),userRegistrationDto.getSemester());
			studentService.add(student);
		}
			break;
		}
		return newUser;
	}
	
	private boolean isEmailAlreadyInUse(String email) {
		return authenticationRepository.existsByUserEmail(email);
	}
}
