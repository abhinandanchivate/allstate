package com.allstate.restdemo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
		
		return ResponseEntity.ok().body(employee);
		
	}
	
	@PostMapping("/employees")
	public ResponseEntity<Void> createEmployee(@Valid @RequestBody Employee employee , UriComponentsBuilder uriComponentsBuilder, HttpServletRequest httpServletRequest ) {
		
		Employee employee2 = employeeRepository.save(employee);
		
		System.out.println(httpServletRequest.getRequestURI());
		
		UriComponents uriComponents = uriComponentsBuilder.path(httpServletRequest.getRequestURL()+"/{id}")
				.buildAndExpand(employee2.getId());
		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
