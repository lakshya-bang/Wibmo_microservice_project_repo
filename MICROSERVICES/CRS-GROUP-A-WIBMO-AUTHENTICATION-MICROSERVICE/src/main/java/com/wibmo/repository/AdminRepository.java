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
	/**
	 * Fetches Admin from the DB on the basis of adminId.
	 * @param adminId
	 * @return
	 */
	Optional<Admin> findByAdminId(Integer adminId);
	/**
	 * Returns a list of admin.
	 */
	List<Admin> findAll();
}
