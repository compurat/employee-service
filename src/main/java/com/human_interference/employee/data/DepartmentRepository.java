package com.human_interference.employee.data;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Department findByName(String departmentName);

    @Cacheable(value = "department", key = "#departmentName")
    boolean existsByName(String departmentName);

    List<Department> findLastById(Long id);
}
