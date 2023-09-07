/**
 * 
 */
package com.wibmo.service;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.dto.UserRegistrationDetailsDTO;
import com.wibmo.dto.User_Creds;
import com.wibmo.repository.AuthenticationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wibmo.entity.*;
import com.wibmo.exception.UserWithEmailAlreadyExistsException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private AuthenticationRepository authenticationRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = authenticationRepository.findByuserEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Set<SimpleGrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority("Role." + user.getUserType().toString()));
		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(),
				roles);
	}


}