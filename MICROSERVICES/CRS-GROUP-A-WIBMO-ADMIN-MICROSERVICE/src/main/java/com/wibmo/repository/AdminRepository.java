package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Admin;

/**
 * 
 */
@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer>{
	
	Optional<Admin> findByAdminId(Integer adminId);
	
	List<Admin> findAll();
}
