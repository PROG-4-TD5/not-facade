package com.example.prog4.repository;

import com.example.prog4.repository.base.entity.Employee;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface EmployeeRepository {
  Employee findById(String id);

  Employee save(Employee employee);
}
