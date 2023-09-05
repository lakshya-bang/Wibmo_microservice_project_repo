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

import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserNotApprovedException;
import com.wibmo.repository.AuthenticationRepository;

public class TestLoginUtil {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private LoginUtil loginUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsApproved_UserApproved_ReturnsTrue() throws UserNotApprovedException {
        String userEmail = "test@example.com";
        User authenticationEntity = new User();
        authenticationEntity.setRegistrationStatus(RegistrationStatus.APPROVED);
        when(authenticationRepository.findByuserEmail(userEmail)).thenReturn(authenticationEntity);

        Boolean result = loginUtil.isApproved(userEmail);

        assertTrue(result);
    }

    @Test
    public void testIsApproved_UserNotApproved_ThrowsUserNotApprovedException() {
        String userEmail = "test@example.com";
        User authenticationEntity = new User();
        authenticationEntity.setRegistrationStatus(RegistrationStatus.PENDING);
        when(authenticationRepository.findByuserEmail(userEmail)).thenReturn(authenticationEntity);

        assertThrows(UserNotApprovedException.class, () -> {
            loginUtil.isApproved(userEmail);
        });
    }
}
