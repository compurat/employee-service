package com.human_interference.employee;

import com.human_interference.employee.data.*;
import com.human_interference.employee.models.EmployeeModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.departmentRepository = departmentRepository;
    }


    public void addEmployee(EmployeeModel employeeModel) {
        if (!employeeRepository.existsByEmail(employeeModel.getEmail())) {
            Employee employee = this.employeeMapper.employeeModelToEmployee(employeeModel);
            Department department = departmentRepository.findByName(employeeModel.getDepartment().getName());

            if (department == null) {
                // Save the new department and assign the generated ID
                department = employee.getDepartment();
                department = departmentRepository.save(department);
            }

            employee.setDepartment(department);
            employeeRepository.save(employee);
        } else {
            throw new IllegalArgumentException("Employee with this email already exists");
        }
    }

    void updateEmployee(EmployeeModel employeeModel) {
        if (employeeRepository.existsByEmail(employeeModel.getEmail())) {
            Employee employee = this.employeeRepository.findByEmail(employeeModel.getEmail());
            employee = this.employeeMapper.updateEmployeeFromUpdateModel(employee,employeeModel);
            departmentRepository.save(employee.getDepartment());
            employeeRepository.save(employee);
        } else {
            throw new IllegalArgumentException("Employee with this email does not exist");
        }
    }

    void deleteEmployee(String email) throws IllegalArgumentException {
        if (employeeRepository.existsByEmail(email)) {
            Employee employee = employeeRepository.findByEmail(email);
            employee.setStatus(EmployementStatus.TERMINATED);
            employeeRepository.save(employee);
        } else {
            throw new IllegalArgumentException("Employee with this email does not exist");
        }
    }

    public EmployeeModel getEmployeeByEmail(String email) throws IllegalArgumentException {
        if (employeeRepository.existsByEmail(email)) {
            return this.employeeMapper.employeeToEmployeeModel(employeeRepository.findByEmail(email));
        } else {
            throw new IllegalArgumentException("Employee with this email does not exist");
        }
    }

    public List<EmployeeModel> findByNameContaining(String partialName) {
        List<Employee> employees;
        if (employeeRepository.existsByNameContainingIgnoreCase(partialName)) {
            employees = this.employeeRepository.findByNameContainingIgnoreCase(partialName);
        } else {
            throw new IllegalArgumentException("Employee with this name (part) does not exist");
        }
        return this.employeeMapper.employeeListToEmployeeModelList(employees);
    }

    public List<EmployeeModel> findBySalaryRange(Float salaryMin, Float salaryMax) {
        List<Employee> employees = this.employeeRepository.findBySalaryBetween(salaryMin, salaryMax);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Employee with this salary range does not exist");
        }
        return this.employeeMapper.employeeListToEmployeeModelList(employees);
    }

    public List<EmployeeModel> findByStatus(EmployementStatus status) {
        List<Employee> employees = this.employeeRepository.findByStatus(status);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Employee with this status does not exist");
        }
        return this.employeeMapper.employeeListToEmployeeModelList(employees);
    }

    public List<EmployeeModel> findByDepartment(String departmentname) {
        Department department;
        if (departmentRepository.existsByName(departmentname)) {
            department = this.departmentRepository.findByName(departmentname);
        } else {
            throw new IllegalArgumentException("Department with this name does not exist");
        }
        return this.employeeMapper.employeeListToEmployeeModelList(department.getEmployees());
    }

    public List<EmployeeModel> findByStartDateRange(LocalDate startDate, LocalDate endDate) {
        List<Employee> employees = this.employeeRepository.findByStartDateBetween(startDate, endDate);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Employee with this start date range does not exist");
        }
        return this.employeeMapper.employeeListToEmployeeModelList(employees);
    }
}
