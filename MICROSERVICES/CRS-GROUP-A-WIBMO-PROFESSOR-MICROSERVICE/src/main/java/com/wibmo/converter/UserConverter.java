package com.wibmo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wibmo.dto.UserResponseDTO;
import com.wibmo.entity.User;

/**
 * 
 */
@Service
public class UserConverter {

	public UserResponseDTO convert(User user) {
		return new UserResponseDTO(
				user.getUserId(),
				user.getUserEmail(),
				user.getUserType(),
				user.getRegistrationStatus());
	}
	
	public List<UserResponseDTO> convertAll(List<User> users) {
		return users
				.stream()
				.map(user -> convert(user))
				.collect(Collectors.toList());
	}
}
