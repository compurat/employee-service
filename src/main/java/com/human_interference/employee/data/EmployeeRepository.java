package com.human_interference.employee.data;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Cacheable(value = "employee", key = "#partialName")
    List<Employee> findByNameContainingIgnoreCase(String partialName);

    List<Employee> findBySalaryBetween(Float minSalary, Float maxSalary);

    List<Employee> findByStatus(EmployementStatus employementStatus);

    List<Employee> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    @Cacheable(value = "employee", key = "#email")
    boolean existsByEmail(String email);

    @Cacheable(value = "employee", key = "#name")
    boolean existsByNameContainingIgnoreCase(@Param("name") String name);

    Employee findByEmail(String email);
}
