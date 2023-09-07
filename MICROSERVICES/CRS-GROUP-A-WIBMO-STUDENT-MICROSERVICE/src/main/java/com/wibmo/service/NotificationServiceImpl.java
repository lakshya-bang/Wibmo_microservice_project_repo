/**
 * 
 */
package com.wibmo.service;

import java.util.List;
import java.util.Optional;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wibmo.entity.Notification;
import com.wibmo.entity.User;
import com.wibmo.repository.UserRepository;
import com.wibmo.utils.JwtTokenUtils;

/**
 * 
 */
@Service
@Component
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Notification> viewNotification(String jwt) {
		
		String userName = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));
		
		Optional<User> user = userRepository.findByUserEmail(userName);
		if(user.isPresent()) {
			 HttpHeaders headers = new HttpHeaders();
			    headers.setBearerAuth(jwt.substring(7));
			    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			    return new RestTemplate().exchange("http://localhost:8086/api/notification/receive-notification/"+user.get().getUserId(),
			    		HttpMethod.GET, entity, 
			    		 new ParameterizedTypeReference<List<Notification>>(){}).getBody();
		}
		return null;
	}

}
