/**
 * 
 */
package com.wibmo.dao;

import org.springframework.stereotype.Repository;

import com.wibmo.entity.User;

/**
 * 
 */

public interface AuthenticationDAO {

    public boolean authenticate(String userEmail, String password);
    public User getUserDetails(String userEmail);

}
