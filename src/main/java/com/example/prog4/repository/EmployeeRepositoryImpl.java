package com.example.prog4.repository;

import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.base.entity.Employee;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
  private final com.example.prog4.repository.base.EmployeeRepository baseEmployeeRepository;
  private final com.example.prog4.repository.cnaps.EmployeeRepository cnapsEmployeeRepository;

  public EmployeeRepositoryImpl(
      @Qualifier("baseEmployeeRepository")
      com.example.prog4.repository.base.EmployeeRepository baseEmployeeRepository,
      @Qualifier("cnapsEmployeeRepository")
      com.example.prog4.repository.cnaps.EmployeeRepository cnapsEmployeeRepository) {
    this.baseEmployeeRepository = baseEmployeeRepository;
    this.cnapsEmployeeRepository = cnapsEmployeeRepository;
  }

  @Override
  public Employee findById(String id) {
    Optional<Employee> base = baseEmployeeRepository.findById(id);
    if (base.isEmpty()) {
      throw new NotFoundException("Employee.Id=" + id + " was not found");
    }
    Optional<com.example.prog4.repository.cnaps.entity.Employee> cnaps =
        cnapsEmployeeRepository.findByEndToEndId(id);
    Employee result;
    result = base.get();
    cnaps.ifPresent(employee -> result.setCnaps(employee.getNumber()));

    return result;
  }

  @Override
  public Employee save(Employee employee) {
    return baseEmployeeRepository.save(employee);
  }
}
