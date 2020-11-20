package com.allstate.restdemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.allstate.restdemo.exception.ResouceNotFoundException;
import com.allstate.restdemo.model.Employee;
import com.allstate.restdemo.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	// using this u can get @Bean, @Component, @Service, @Repository
	// object directly
	
	@Autowired 
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		// select * from employee
		return employeeRepository.findAll();
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") long empId) throws ResouceNotFoundException {
		Employee employee = employeeRepository.findById(empId).orElseThrow(()->new ResouceNotFoundException("Data not found"));
		
		return null;
		
	}
}
