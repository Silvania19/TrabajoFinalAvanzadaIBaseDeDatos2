package com.utn.TPfinal.service;

import com.utn.TPfinal.domain.Employee;
import com.utn.TPfinal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    public Employee findByNameAndPassword(String name, String password) {
        return employeeRepository.findByNameAndPassword(name, password);
    }

    public void add(Employee user) {
      employeeRepository.save(user);
    }
}
