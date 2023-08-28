/**
 * 
 */
package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Student;

/**
 * 
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>{
}
