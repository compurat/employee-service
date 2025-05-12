package com.human_interference.employee;

import com.human_interference.employee.data.Department;
import com.human_interference.employee.data.Employee;
import com.human_interference.employee.models.DepartmentModel;
import com.human_interference.employee.models.EmployeeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    List<EmployeeModel> employeeListToEmployeeModelList(List<Employee> employees) {
        return employees.stream()
                .map(this::employeeToEmployeeModel)
                .collect(Collectors.toList());
    }

    EmployeeModel employeeToEmployeeModel(Employee employee) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setAddress(employee.getAddress());
        employeeModel.setFirstName(employee.getFirstName());
        employeeModel.setName(employee.getName());
        employeeModel.setSalary(employee.getSalary());
        employeeModel.setEmail(employee.getEmail());
        employeeModel.setPhoneNumber(employee.getPhoneNumber());
        employeeModel.setStartDate(employee.getStartDate());
        employeeModel.setEndDate(employee.getEndDate());
        employeeModel.setStatus(employee.getStatus());
        employeeModel.setDepartment(departmentToDepartmentModel(employee.getDepartment()));
        return employeeModel;
    }

    Employee employeeModelToEmployee(EmployeeModel employeeModel) {
        Employee employee = new Employee();
        employee.setAddress(employeeModel.getAddress());
        employee.setFirstName(employeeModel.getFirstName());
        employee.setName(employeeModel.getName());
        employee.setSalary(employeeModel.getSalary());
        employee.setEmail(employeeModel.getEmail());
        employee.setPhoneNumber(employeeModel.getPhoneNumber());
        employee.setStartDate(employeeModel.getStartDate());
        employee.setEndDate(employeeModel.getEndDate());
        employee.setStatus(employeeModel.getStatus());
        employee.setDepartment(departmentModelToDepartment(employeeModel.getDepartment()));
        return employee;
    }

    Employee updateEmployeeFromUpdateModel(Employee employee, EmployeeModel employeeModel) {
        employee.setAddress(employeeModel.getAddress());
        employee.setFirstName(employeeModel.getFirstName());
        employee.setName(employeeModel.getName());
        employee.setSalary(employeeModel.getSalary());
        employee.setEmail(employeeModel.getEmail());
        employee.setPhoneNumber(employeeModel.getPhoneNumber());
        employee.setStartDate(employeeModel.getStartDate());
        employee.setEndDate(employeeModel.getEndDate());
        employee.setStatus(employeeModel.getStatus());
        employee.setDepartment(updateDepartmentModelFromUpdateModel(employee.getDepartment(), employeeModel.getDepartment()));
        return employee;
    }

    private Department updateDepartmentModelFromUpdateModel(Department department, DepartmentModel departmentModel) {
        department.setName(departmentModel.getName());
        department.setPhoneNumber(departmentModel.getPhoneNumber());
        department.setDescription(departmentModel.getDescription());
        return department;
    }

    private Department departmentModelToDepartment(DepartmentModel departmentModel) {
        Department department = new Department();
        department.setName(departmentModel.getName());
        department.setPhoneNumber(departmentModel.getPhoneNumber());
        department.setDescription(departmentModel.getDescription());
        return department;
    }

    private DepartmentModel departmentToDepartmentModel(Department department) {
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setName(department.getName());
        departmentModel.setPhoneNumber(department.getPhoneNumber());
        departmentModel.setDescription(department.getDescription());
        return departmentModel;
    }

}
