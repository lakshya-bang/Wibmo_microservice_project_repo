package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Student getStudentById(Integer studentId) {
		Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
		return studentOptional.isPresent()
				? studentOptional.get()
				: null;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds) {
		return studentRepository.findAllByStudentIdIn(studentIds);
	}

	@Override
	public void add(Student student) {
		
		if(null == student) {
			return;
		}
		
		studentRepository.save(student);
	}
	
	@Override
	public Boolean isStudentExistsById(Integer studentId) {
		return studentRepository
				.findByStudentId(studentId)
				.isPresent();
	}
	
	@Override
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> studentIds) {
		return studentRepository
				.findAllByStudentIdIn(studentIds)
				.stream()
				.collect(Collectors.toMap(
						Student::getStudentId,
						Function.identity()));
	}
	
	@Override
	public Integer getCurrentSemesterByStudentId(Integer studentId) {
		return studentRepository
				.findByStudentId(studentId)
				.get()
				.getCurrentSemester();
	}
	
	/***************************** Utility methods ********************************/
	
	private Integer getUserId(Integer studentId) {
		return studentRepository
				.findById(studentId)
				.get()
				.getUserId();
	}
	
}
