package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Admin;

/**
 * 
 */
@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer>{
	
}
