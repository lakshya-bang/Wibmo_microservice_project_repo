/**
 * 
 */
package com.wibmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.service.CourseOperationImpl;

/**
 * 
 */
@RestController
public class CourseController {
	
	@Autowired
	private CourseOperationImpl courseOperation;
	
	
	
}
