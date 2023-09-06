/**
 * 
 */
package com.wibmo.utils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.UserType;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;
import com.wibmo.repository.AuthenticationRepository;
import com.wibmo.service.AdminService;
import com.wibmo.service.AdminServiceImpl;
import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.service.StudentServiceImpl;

public class TestUserRegistrationUtil {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private AdminServiceImpl adminService;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private ProfessorServiceImpl professorService;

    @InjectMocks
    private UserRegistrationUtil userRegistrationUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveRegDetailsUser_EmailAlreadyInUse_ThrowsUserWithEmailAlreadyExistsException() {
        String userEmail = "test@example.com";
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        userRegistrationDto.setUserEmail(userEmail);

        when(authenticationRepository.existsByUserEmail(userEmail)).thenReturn(true);

        assertThrows(UserWithEmailAlreadyExistsException.class, () -> {
            userRegistrationUtil.saveRegDetailsUser(userRegistrationDto);
        });
    }

    @Test
    public void testSaveRegDetailsUser_AdminUserType_CallsAdminServiceAdd() throws UserWithEmailAlreadyExistsException {
        String userEmail = "test@example.com";
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        userRegistrationDto.setUserEmail(userEmail);
        userRegistrationDto.setUserType(UserType.ADMIN);

        when(authenticationRepository.existsByUserEmail(userEmail)).thenReturn(false);

        User result = userRegistrationUtil.saveRegDetailsUser(userRegistrationDto);

        verify(adminService, times(1)).add(any(Admin.class));
        assertEquals(userEmail, result.getUserEmail());
    }

    @Test
    public void testSaveRegDetailsUser_ProfessorUserType_CallsProfessorServiceAdd() throws UserWithEmailAlreadyExistsException {
        String userEmail = "test@example.com";
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        userRegistrationDto.setUserEmail(userEmail);
        userRegistrationDto.setUserType(UserType.PROFESSOR);

        when(authenticationRepository.existsByUserEmail(userEmail)).thenReturn(false);

        User result = userRegistrationUtil.saveRegDetailsUser(userRegistrationDto);

        verify(professorService, times(1)).add(any(Professor.class));
        assertEquals(userEmail, result.getUserEmail());
    }

    @Test
    public void testSaveRegDetailsUser_StudentUserType_CallsStudentServiceAdd() throws UserWithEmailAlreadyExistsException {
        String userEmail = "test@example.com";
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        userRegistrationDto.setUserEmail(userEmail);
        userRegistrationDto.setUserType(UserType.STUDENT);

        when(authenticationRepository.existsByUserEmail(userEmail)).thenReturn(false);

        User result = userRegistrationUtil.saveRegDetailsUser(userRegistrationDto);

        verify(studentService, times(1)).add(any(Student.class));
        assertEquals(userEmail, result.getUserEmail());
    }
}