package com.utn.TPfinal.repository;

import org.springframework.stereotype.Repository;
import com.utn.TPfinal.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByNameAndPassword(String name, String password);
}
