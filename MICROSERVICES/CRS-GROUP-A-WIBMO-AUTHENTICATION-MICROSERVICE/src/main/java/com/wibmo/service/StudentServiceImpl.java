package com.wibmo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentRepository studentRepository;


	@Override
	public void add(Student student) {
		if(null == student) {
			return;
		}
		studentRepository.save(student);
	}
	

	
}
