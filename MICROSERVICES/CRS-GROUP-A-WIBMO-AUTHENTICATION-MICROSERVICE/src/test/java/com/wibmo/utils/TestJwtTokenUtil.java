package com.wibmo.utils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TestJwtTokenUtil {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsernameFromToken() {
        String token = "sampleToken";
        when(jwtTokenUtil.getClaimFromToken(token, Claims::getSubject)).thenReturn("username");

        String username = jwtTokenUtil.getUsernameFromToken(token);

        assertEquals("username", username);
    }

    @Test
    public void testGetIssuedAtDateFromToken() {
        String token = "sampleToken";
        Date issuedAtDate = new Date();
        when(jwtTokenUtil.getClaimFromToken(token, Claims::getIssuedAt)).thenReturn(issuedAtDate);

        Date result = jwtTokenUtil.getIssuedAtDateFromToken(token);

        assertEquals(issuedAtDate, result);
    }

    @Test
    public void testGetExpirationDateFromToken() {
        String token = "sampleToken";
        Date expirationDate = new Date();
        when(jwtTokenUtil.getClaimFromToken(token, Claims::getExpiration)).thenReturn(expirationDate);

        Date result = jwtTokenUtil.getExpirationDateFromToken(token);

        assertEquals(expirationDate, result);
    }


}
